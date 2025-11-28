#!/usr/bin/env python3
import urllib.request
import os
from bs4 import BeautifulSoup

URL = "https://meta.wikimedia.org/wiki/List_of_Wikipedias"
TARGET_FILE = "app/src/main/java/com/anysoftkeyboard/janus/app/util/SupportedLanguages.kt"

def fetch_html(url):
    print(f"Fetching {url}...")
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    with urllib.request.urlopen(req) as response:
        return response.read().decode('utf-8')

def parse_int(text):
    if not text:
        return 0
    # Remove commas and non-breaking spaces
    clean = text.replace(',', '').replace('\xa0', '').strip()
    try:
        return int(clean)
    except ValueError:
        return 0

def parse_languages(html):
    print("Parsing languages...")
    soup = BeautifulSoup(html, 'html.parser')

    # Find the table
    # Look for the section
    # <span class="mw-headline" id="All_Wikipedias_ordered_by_number_of_articles">...</span>
    # Then the next table

    header = soup.find(id="All_Wikipedias_ordered_by_number_of_articles")
    if not header:
        # Try finding by text
        for h in soup.find_all(['h2', 'h3']):
            if "All Wikipedias ordered by number of articles" in h.text:
                header = h
                break

    if not header:
        raise ValueError("Could not find table section")

    # Find next table
    table = header.find_next('table', {'class': 'wikitable'})
    if not table:
        raise ValueError("Could not find table")

    languages = []

    # Skip header row
    rows = table.find_all('tr')[1:]

    for row in rows:
        cols = row.find_all('td')
        if not cols or len(cols) < 10:
            continue

        # Col 1: Name (English)
        # Col 2: Local Name
        # Col 3: Wiki Code
        # Col 4: Articles
        # Col 5: All pages
        # ...
        # Col 9: Active Users (Index 9, so 10th column? Let's check headers)

        # Headers: No, Language, Language (local), Wiki, Articles, All pages, Edits, Admins, Users, Active users, Files, Depth
        # Indices: 0, 1,        2,                 3,    4,        5,         6,     7,      8,     9,            10,    11

        # Check col 3 (Wiki Code)
        code_link = cols[3].find('a')
        if not code_link:
            continue
        code = code_link.text.strip()

        # Name (Col 1)
        name_link = cols[1].find('a')
        name = name_link.text.strip() if name_link else cols[1].text.strip()

        # Local Name (Col 2)
        # Might be in <bdi> or plain text
        local_name = cols[2].text.strip()

        # Articles (Col 4)
        article_count = parse_int(cols[4].text)

        # Pages (Col 5)
        page_count = parse_int(cols[5].text)

        # Active Users (Col 9)
        active_user_count = parse_int(cols[9].text)

        languages.append({
            'code': code,
            'name': name,
            'localName': local_name,
            'articleCount': article_count,
            'pageCount': page_count,
            'activeUserCount': active_user_count
        })

    return languages

def generate_kotlin_file(languages, filepath):
    print(f"Generating {filepath}...")

    # Sort: Top 15 by article count desc, rest alphabetical by code
    languages.sort(key=lambda x: x['articleCount'], reverse=True)

    top_15 = languages[:15]
    rest = languages[15:]
    rest.sort(key=lambda x: x['name'])

    final_list = top_15 + rest

    content = """package com.anysoftkeyboard.janus.app.util

data class SupportedLanguage(
    val code: String,
    val name: String,
    val localName: String,
    val articleCount: Int,
    val pageCount: Int,
    val activeUserCount: Int
)

val supportedLanguages = listOf(
"""
    for lang in final_list:
        # Escape quotes in strings if any
        name = lang['name'].replace('"', '\\"')
        local_name = lang['localName'].replace('"', '\\"')
        # local name might have newlines or weird chars? strip() handled it.

        content += f'    SupportedLanguage(\n'
        content += f'        code = "{lang["code"]}",\n'
        content += f'        name = "{name}",\n'
        content += f'        localName = "{local_name}",\n'
        content += f'        articleCount = {lang["articleCount"]},\n'
        content += f'        pageCount = {lang["pageCount"]},\n'
        content += f'        activeUserCount = {lang["activeUserCount"]}\n'
        content += f'    ),\n'
    content += ")\n"

    # Ensure directory exists
    os.makedirs(os.path.dirname(filepath), exist_ok=True)

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("Done.")

def main():
    try:
        html = fetch_html(URL)
        languages = parse_languages(html)
        if not languages:
            print("No languages found!")
            exit(1)
        print(f"Found {len(languages)} languages.")
        generate_kotlin_file(languages, TARGET_FILE)
    except Exception as e:
        import traceback
        traceback.print_exc()
        exit(1)

if __name__ == "__main__":
    main()
