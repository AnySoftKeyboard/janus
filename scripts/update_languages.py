#!/usr/bin/env python3
import re
import urllib.request
import os

URL = "https://meta.wikimedia.org/wiki/List_of_Wikipedias"
TARGET_FILE = "app/src/main/java/com/anysoftkeyboard/janus/app/util/SupportedLanguages.kt"

def fetch_html(url):
    print(f"Fetching {url}...")
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    with urllib.request.urlopen(req) as response:
        return response.read().decode('utf-8')

def parse_languages(html):
    print("Parsing languages...")
    # Find the table "All Wikipedias ordered by number of articles"
    section_markers = [
        'id="All_Wikipedias_ordered_by_number_of_articles"',
        'All Wikipedias ordered by number of articles'
    ]

    start_pos = -1
    for marker in section_markers:
        start_pos = html.find(marker)
        if start_pos != -1:
            break

    if start_pos == -1:
        raise ValueError("Could not find table section start")

    table_start = html.find('<table', start_pos)
    if table_start == -1:
        raise ValueError("Could not find table start tag")

    table_end = html.find('</table>', table_start)
    if table_end == -1:
        raise ValueError("Could not find table end tag")

    table_content = html[table_start:table_end]
    rows = table_content.split('</tr>')

    languages = []

    for row in rows:
        if 'Special:Statistics' not in row:
            continue

        # Extract code
        # Look for the link to the main page which usually has the code as text and title="code:"
        # Example: <a href="..." class="extiw" title="en:">en</a>
        # Regex: title="([^"]+):">([^<]+)</a>

        code_match = re.search(r'title="([^"]+):">([^<]+)</a>', row)

        # Extract count
        # Example: <a href="..." title="...">7,096,419</a>
        # It links to Special:Statistics
        count_match = re.search(r'Special:Statistics"[^>]*>([0-9,]+)</a>', row)

        if code_match and count_match:
            code = code_match.group(2).strip()
            count_str = count_match.group(1).replace(',', '')
            try:
                count = int(count_str)
                languages.append((code, count))
            except ValueError:
                continue

    return languages

def generate_kotlin_file(languages, filepath):
    print(f"Generating {filepath}...")

    # Sort: Top 15 by count desc, rest alphabetical
    languages.sort(key=lambda x: x[1], reverse=True)

    top_15 = [x[0] for x in languages[:15]]
    rest = [x[0] for x in languages[15:]]
    rest.sort()

    final_list = top_15 + rest

    content = """package com.anysoftkeyboard.janus.app.util

val supportedLanguages = listOf(
"""
    for lang in final_list:
        content += f'    "{lang}",\n'
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
        print(f"Error: {e}")
        exit(1)

if __name__ == "__main__":
    main()
