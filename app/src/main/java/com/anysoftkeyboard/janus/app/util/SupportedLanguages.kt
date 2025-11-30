package com.anysoftkeyboard.janus.app.util

data class SupportedLanguage(
    val code: String,
    val name: String,
    val localName: String,
    val articleCount: Int,
    val pageCount: Int,
    val activeUserCount: Int
)

val supportedLanguages = listOf(
    SupportedLanguage(
        code = "en",
        name = "English",
        localName = "English",
        articleCount = 7097802,
        pageCount = 64557501,
        activeUserCount = 278786
    ),
    SupportedLanguage(
        code = "ceb",
        name = "Cebuano",
        localName = "Cebuano",
        articleCount = 6115858,
        pageCount = 11230187,
        activeUserCount = 191
    ),
    SupportedLanguage(
        code = "de",
        name = "German",
        localName = "Deutsch",
        articleCount = 3073275,
        pageCount = 8429347,
        activeUserCount = 40198
    ),
    SupportedLanguage(
        code = "fr",
        name = "French",
        localName = "français",
        articleCount = 2722823,
        pageCount = 13747841,
        activeUserCount = 42387
    ),
    SupportedLanguage(
        code = "sv",
        name = "Swedish",
        localName = "svenska",
        articleCount = 2619682,
        pageCount = 6343919,
        activeUserCount = 5214
    ),
    SupportedLanguage(
        code = "nl",
        name = "Dutch",
        localName = "Nederlands",
        articleCount = 2203888,
        pageCount = 4743679,
        activeUserCount = 9448
    ),
    SupportedLanguage(
        code = "es",
        name = "Spanish",
        localName = "español",
        articleCount = 2077126,
        pageCount = 8539570,
        activeUserCount = 29897
    ),
    SupportedLanguage(
        code = "ru",
        name = "Russian",
        localName = "русский",
        articleCount = 2073735,
        pageCount = 8429332,
        activeUserCount = 7671
    ),
    SupportedLanguage(
        code = "it",
        name = "Italian",
        localName = "italiano",
        articleCount = 1946197,
        pageCount = 8482316,
        activeUserCount = 33231
    ),
    SupportedLanguage(
        code = "pl",
        name = "Polish",
        localName = "polski",
        articleCount = 1676260,
        pageCount = 3959083,
        activeUserCount = 12244
    ),
    SupportedLanguage(
        code = "arz",
        name = "Egyptian Arabic",
        localName = "مصرى",
        articleCount = 1629708,
        pageCount = 2202367,
        activeUserCount = 368
    ),
    SupportedLanguage(
        code = "zh",
        name = "Chinese",
        localName = "中文",
        articleCount = 1513035,
        pageCount = 8247305,
        activeUserCount = 16992
    ),
    SupportedLanguage(
        code = "ja",
        name = "Japanese",
        localName = "日本語",
        articleCount = 1481947,
        pageCount = 4354174,
        activeUserCount = 26384
    ),
    SupportedLanguage(
        code = "uk",
        name = "Ukrainian",
        localName = "українська",
        articleCount = 1398455,
        pageCount = 5076351,
        activeUserCount = 6253
    ),
    SupportedLanguage(
        code = "vi",
        name = "Vietnamese",
        localName = "Tiếng Việt",
        articleCount = 1296523,
        pageCount = 13096289,
        activeUserCount = 3936
    ),
    SupportedLanguage(
        code = "ab",
        name = "Abkhazian",
        localName = "аԥсшәа",
        articleCount = 6478,
        pageCount = 32905,
        activeUserCount = 33
    ),
    SupportedLanguage(
        code = "ace",
        name = "Acehnese",
        localName = "Acèh",
        articleCount = 13010,
        pageCount = 27975,
        activeUserCount = 65
    ),
    SupportedLanguage(
        code = "ady",
        name = "Adyghe",
        localName = "адыгабзэ",
        articleCount = 618,
        pageCount = 4697,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "af",
        name = "Afrikaans",
        localName = "Afrikaans",
        articleCount = 127298,
        pageCount = 433497,
        activeUserCount = 301
    ),
    SupportedLanguage(
        code = "sq",
        name = "Albanian",
        localName = "shqip",
        articleCount = 104746,
        pageCount = 320647,
        activeUserCount = 484
    ),
    SupportedLanguage(
        code = "als",
        name = "Alemannic",
        localName = "Alemannisch",
        articleCount = 31405,
        pageCount = 74706,
        activeUserCount = 135
    ),
    SupportedLanguage(
        code = "am",
        name = "Amharic",
        localName = "አማርኛ",
        articleCount = 15458,
        pageCount = 46431,
        activeUserCount = 86
    ),
    SupportedLanguage(
        code = "ami",
        name = "Amis",
        localName = "Pangcah",
        articleCount = 1145,
        pageCount = 2253,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "anp",
        name = "Angika",
        localName = "अंगिका",
        articleCount = 1669,
        pageCount = 5279,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "ar",
        name = "Arabic",
        localName = "العربية",
        articleCount = 1287806,
        pageCount = 8979043,
        activeUserCount = 7416
    ),
    SupportedLanguage(
        code = "an",
        name = "Aragonese",
        localName = "aragonés",
        articleCount = 50725,
        pageCount = 183632,
        activeUserCount = 85
    ),
    SupportedLanguage(
        code = "rki",
        name = "Arakanese",
        localName = "ရခိုင်",
        articleCount = 1082,
        pageCount = 3228,
        activeUserCount = 38
    ),
    SupportedLanguage(
        code = "arc",
        name = "Aramaic",
        localName = "ܐܪܡܝܐ",
        articleCount = 1916,
        pageCount = 6574,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "hy",
        name = "Armenian",
        localName = "հայերեն",
        articleCount = 323229,
        pageCount = 1186530,
        activeUserCount = 761
    ),
    SupportedLanguage(
        code = "roa-rup",
        name = "Aromanian",
        localName = "armãneashti",
        articleCount = 1389,
        pageCount = 4623,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "frp",
        name = "Arpitan",
        localName = "arpetan",
        articleCount = 5814,
        pageCount = 18026,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "as",
        name = "Assamese",
        localName = "অসমীয়া",
        articleCount = 20503,
        pageCount = 116522,
        activeUserCount = 170
    ),
    SupportedLanguage(
        code = "ast",
        name = "Asturian",
        localName = "asturianu",
        articleCount = 138215,
        pageCount = 247205,
        activeUserCount = 188
    ),
    SupportedLanguage(
        code = "tay",
        name = "Atayal",
        localName = "Tayal",
        articleCount = 2582,
        pageCount = 3292,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "atj",
        name = "Atikamekw",
        localName = "Atikamekw",
        articleCount = 2076,
        pageCount = 3289,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "av",
        name = "Avaric",
        localName = "авар",
        articleCount = 4003,
        pageCount = 18792,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "awa",
        name = "Awadhi",
        localName = "अवधी",
        articleCount = 2550,
        pageCount = 6219,
        activeUserCount = 33
    ),
    SupportedLanguage(
        code = "ay",
        name = "Aymara",
        localName = "Aymar aru",
        articleCount = 5246,
        pageCount = 8917,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "az",
        name = "Azerbaijani",
        localName = "azərbaycanca",
        articleCount = 209525,
        pageCount = 637363,
        activeUserCount = 1133
    ),
    SupportedLanguage(
        code = "ban",
        name = "Balinese",
        localName = "Basa Bali",
        articleCount = 34436,
        pageCount = 70282,
        activeUserCount = 143
    ),
    SupportedLanguage(
        code = "bm",
        name = "Bambara",
        localName = "bamanankan",
        articleCount = 921,
        pageCount = 3337,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "bn",
        name = "Bangla",
        localName = "বাংলা",
        articleCount = 177808,
        pageCount = 1368428,
        activeUserCount = 2341
    ),
    SupportedLanguage(
        code = "bjn",
        name = "Banjar",
        localName = "Banjar",
        articleCount = 11447,
        pageCount = 34948,
        activeUserCount = 70
    ),
    SupportedLanguage(
        code = "map-bms",
        name = "Banyumasan",
        localName = "Basa Banyumasan",
        articleCount = 13940,
        pageCount = 30445,
        activeUserCount = 57
    ),
    SupportedLanguage(
        code = "ba",
        name = "Bashkir",
        localName = "башҡортса",
        articleCount = 63915,
        pageCount = 183917,
        activeUserCount = 75
    ),
    SupportedLanguage(
        code = "eu",
        name = "Basque",
        localName = "euskara",
        articleCount = 476291,
        pageCount = 1022187,
        activeUserCount = 1407
    ),
    SupportedLanguage(
        code = "btm",
        name = "Batak Mandailing",
        localName = "Batak Mandailing",
        articleCount = 1188,
        pageCount = 2667,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "bbc",
        name = "Batak Toba",
        localName = "Batak Toba",
        articleCount = 1263,
        pageCount = 2338,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "bar",
        name = "Bavarian",
        localName = "Boarisch",
        articleCount = 27202,
        pageCount = 110579,
        activeUserCount = 69
    ),
    SupportedLanguage(
        code = "be",
        name = "Belarusian",
        localName = "беларуская",
        articleCount = 257517,
        pageCount = 719941,
        activeUserCount = 423
    ),
    SupportedLanguage(
        code = "be-tarask",
        name = "Belarusian (Taraškievica orthography)",
        localName = "беларуская (тарашкевіца)",
        articleCount = 90049,
        pageCount = 263925,
        activeUserCount = 246
    ),
    SupportedLanguage(
        code = "bew",
        name = "Betawi",
        localName = "Betawi",
        articleCount = 3076,
        pageCount = 7442,
        activeUserCount = 61
    ),
    SupportedLanguage(
        code = "bh",
        name = "Bhojpuri",
        localName = "भोजपुरी",
        articleCount = 8858,
        pageCount = 80616,
        activeUserCount = 42
    ),
    SupportedLanguage(
        code = "bpy",
        name = "Bishnupriya",
        localName = "বিষ্ণুপ্রিয়া মণিপুরী",
        articleCount = 25092,
        pageCount = 63452,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "bi",
        name = "Bislama",
        localName = "Bislama",
        articleCount = 1478,
        pageCount = 3442,
        activeUserCount = 20
    ),
    SupportedLanguage(
        code = "bs",
        name = "Bosnian",
        localName = "bosanski",
        articleCount = 96209,
        pageCount = 381833,
        activeUserCount = 296
    ),
    SupportedLanguage(
        code = "br",
        name = "Breton",
        localName = "brezhoneg",
        articleCount = 89924,
        pageCount = 160147,
        activeUserCount = 133
    ),
    SupportedLanguage(
        code = "bug",
        name = "Buginese",
        localName = "Basa Ugi",
        articleCount = 15956,
        pageCount = 20476,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "bg",
        name = "Bulgarian",
        localName = "български",
        articleCount = 306718,
        pageCount = 694380,
        activeUserCount = 2333
    ),
    SupportedLanguage(
        code = "my",
        name = "Burmese",
        localName = "မြန်မာဘာသာ",
        articleCount = 109832,
        pageCount = 261117,
        activeUserCount = 355
    ),
    SupportedLanguage(
        code = "zh-yue",
        name = "Cantonese",
        localName = "粵語",
        articleCount = 148161,
        pageCount = 334153,
        activeUserCount = 1024
    ),
    SupportedLanguage(
        code = "ca",
        name = "Catalan",
        localName = "català",
        articleCount = 784823,
        pageCount = 1969912,
        activeUserCount = 3272
    ),
    SupportedLanguage(
        code = "bcl",
        name = "Central Bikol",
        localName = "Bikol Central",
        articleCount = 21218,
        pageCount = 50040,
        activeUserCount = 100
    ),
    SupportedLanguage(
        code = "dtp",
        name = "Central Dusun",
        localName = "Kadazandusun",
        articleCount = 1714,
        pageCount = 7231,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "knc",
        name = "Central Kanuri",
        localName = "Yerwa Kanuri",
        articleCount = 1543,
        pageCount = 2435,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "ckb",
        name = "Central Kurdish",
        localName = "کوردی",
        articleCount = 79283,
        pageCount = 251952,
        activeUserCount = 199
    ),
    SupportedLanguage(
        code = "ch",
        name = "Chamorro",
        localName = "Chamoru",
        articleCount = 558,
        pageCount = 2589,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "cbk-zam",
        name = "Chavacano",
        localName = "Chavacano de Zamboanga",
        articleCount = 3236,
        pageCount = 8991,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "ce",
        name = "Chechen",
        localName = "нохчийн",
        articleCount = 688322,
        pageCount = 1322256,
        activeUserCount = 73
    ),
    SupportedLanguage(
        code = "chr",
        name = "Cherokee",
        localName = "ᏣᎳᎩ",
        articleCount = 1004,
        pageCount = 4068,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "chy",
        name = "Cheyenne",
        localName = "Tsetsêhestâhese",
        articleCount = 726,
        pageCount = 2285,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "cu",
        name = "Church Slavic",
        localName = "словѣньскъ / ⰔⰎⰑⰂⰡⰐⰠⰔⰍⰟ",
        articleCount = 1312,
        pageCount = 5887,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "cv",
        name = "Chuvash",
        localName = "чӑвашла",
        articleCount = 58141,
        pageCount = 121040,
        activeUserCount = 55
    ),
    SupportedLanguage(
        code = "ksh",
        name = "Colognian",
        localName = "Ripoarisch",
        articleCount = 3037,
        pageCount = 10798,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "kw",
        name = "Cornish",
        localName = "kernowek",
        articleCount = 7097,
        pageCount = 14778,
        activeUserCount = 32
    ),
    SupportedLanguage(
        code = "co",
        name = "Corsican",
        localName = "corsu",
        articleCount = 8604,
        pageCount = 18018,
        activeUserCount = 63
    ),
    SupportedLanguage(
        code = "cr",
        name = "Cree",
        localName = "Nēhiyawēwin / ᓀᐦᐃᔭᐍᐏᐣ",
        articleCount = 14,
        pageCount = 2344,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "crh",
        name = "Crimean Tatar",
        localName = "qırımtatarca",
        articleCount = 29622,
        pageCount = 57546,
        activeUserCount = 45
    ),
    SupportedLanguage(
        code = "hr",
        name = "Croatian",
        localName = "hrvatski",
        articleCount = 228571,
        pageCount = 493634,
        activeUserCount = 1424
    ),
    SupportedLanguage(
        code = "cs",
        name = "Czech",
        localName = "čeština",
        articleCount = 581154,
        pageCount = 1620376,
        activeUserCount = 5733
    ),
    SupportedLanguage(
        code = "dag",
        name = "Dagbani",
        localName = "dagbanli",
        articleCount = 13338,
        pageCount = 24349,
        activeUserCount = 37
    ),
    SupportedLanguage(
        code = "da",
        name = "Danish",
        localName = "dansk",
        articleCount = 311609,
        pageCount = 977073,
        activeUserCount = 1842
    ),
    SupportedLanguage(
        code = "diq",
        name = "Dimli",
        localName = "Zazaki",
        articleCount = 42353,
        pageCount = 63899,
        activeUserCount = 38
    ),
    SupportedLanguage(
        code = "din",
        name = "Dinka",
        localName = "Thuɔŋjäŋ",
        articleCount = 339,
        pageCount = 1128,
        activeUserCount = 8
    ),
    SupportedLanguage(
        code = "dv",
        name = "Divehi",
        localName = "ދިވެހިބަސް",
        articleCount = 3192,
        pageCount = 12507,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "dty",
        name = "Doteli",
        localName = "डोटेली",
        articleCount = 3631,
        pageCount = 22208,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "dz",
        name = "Dzongkha",
        localName = "ཇོང་ཁ",
        articleCount = 378,
        pageCount = 5497,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "mhr",
        name = "Eastern Mari",
        localName = "олык марий",
        articleCount = 11327,
        pageCount = 31238,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "eml",
        name = "Emiliano-Romagnolo",
        localName = "emiliàn e rumagnòl",
        articleCount = 13732,
        pageCount = 36815,
        activeUserCount = 63
    ),
    SupportedLanguage(
        code = "myv",
        name = "Erzya",
        localName = "эрзянь",
        articleCount = 7867,
        pageCount = 31489,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "eo",
        name = "Esperanto",
        localName = "Esperanto",
        articleCount = 378550,
        pageCount = 849290,
        activeUserCount = 512
    ),
    SupportedLanguage(
        code = "et",
        name = "Estonian",
        localName = "eesti",
        articleCount = 256135,
        pageCount = 603750,
        activeUserCount = 1462
    ),
    SupportedLanguage(
        code = "ee",
        name = "Ewe",
        localName = "eʋegbe",
        articleCount = 1268,
        pageCount = 4252,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "ext",
        name = "Extremaduran",
        localName = "estremeñu",
        articleCount = 4071,
        pageCount = 9149,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "fat",
        name = "Fanti",
        localName = "mfantse",
        articleCount = 1764,
        pageCount = 4720,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "fo",
        name = "Faroese",
        localName = "føroyskt",
        articleCount = 14179,
        pageCount = 41109,
        activeUserCount = 57
    ),
    SupportedLanguage(
        code = "hif",
        name = "Fiji Hindi",
        localName = "Fiji Hindi",
        articleCount = 12076,
        pageCount = 55137,
        activeUserCount = 72
    ),
    SupportedLanguage(
        code = "fj",
        name = "Fijian",
        localName = "Na Vosa Vakaviti",
        articleCount = 1612,
        pageCount = 4352,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "fi",
        name = "Finnish",
        localName = "suomi",
        articleCount = 608497,
        pageCount = 1562381,
        activeUserCount = 3757
    ),
    SupportedLanguage(
        code = "fon",
        name = "Fon",
        localName = "fɔ̀ngbè",
        articleCount = 3335,
        pageCount = 5063,
        activeUserCount = 42
    ),
    SupportedLanguage(
        code = "gur",
        name = "Frafra",
        localName = "farefare",
        articleCount = 1333,
        pageCount = 2525,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "fur",
        name = "Friulian",
        localName = "furlan",
        articleCount = 4886,
        pageCount = 11194,
        activeUserCount = 39
    ),
    SupportedLanguage(
        code = "ff",
        name = "Fula",
        localName = "Fulfulde",
        articleCount = 13102,
        pageCount = 27666,
        activeUserCount = 50
    ),
    SupportedLanguage(
        code = "gag",
        name = "Gagauz",
        localName = "Gagauz",
        articleCount = 3013,
        pageCount = 7801,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "gl",
        name = "Galician",
        localName = "galego",
        articleCount = 227980,
        pageCount = 560013,
        activeUserCount = 506
    ),
    SupportedLanguage(
        code = "gan",
        name = "Gan",
        localName = "贛語",
        articleCount = 6815,
        pageCount = 34473,
        activeUserCount = 43
    ),
    SupportedLanguage(
        code = "lg",
        name = "Ganda",
        localName = "Luganda",
        articleCount = 4416,
        pageCount = 8756,
        activeUserCount = 45
    ),
    SupportedLanguage(
        code = "ka",
        name = "Georgian",
        localName = "ქართული",
        articleCount = 187846,
        pageCount = 522949,
        activeUserCount = 509
    ),
    SupportedLanguage(
        code = "gpe",
        name = "Ghanaian Pidgin",
        localName = "Ghanaian Pidgin",
        articleCount = 4082,
        pageCount = 21893,
        activeUserCount = 29
    ),
    SupportedLanguage(
        code = "glk",
        name = "Gilaki",
        localName = "گیلکی",
        articleCount = 48352,
        pageCount = 57650,
        activeUserCount = 34
    ),
    SupportedLanguage(
        code = "gom",
        name = "Goan Konkani",
        localName = "गोंयची कोंकणी / Gõychi Konknni",
        articleCount = 3643,
        pageCount = 8906,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "gor",
        name = "Gorontalo",
        localName = "Bahasa Hulontalo",
        articleCount = 14907,
        pageCount = 24426,
        activeUserCount = 46
    ),
    SupportedLanguage(
        code = "got",
        name = "Gothic",
        localName = "𐌲𐌿𐍄𐌹𐍃𐌺",
        articleCount = 979,
        pageCount = 3951,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "el",
        name = "Greek",
        localName = "Ελληνικά",
        articleCount = 261651,
        pageCount = 735868,
        activeUserCount = 2728
    ),
    SupportedLanguage(
        code = "gn",
        name = "Guarani",
        localName = "Avañe'ẽ",
        articleCount = 5980,
        pageCount = 14071,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "gcr",
        name = "Guianan Creole",
        localName = "kriyòl gwiyannen",
        articleCount = 1075,
        pageCount = 2670,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "gu",
        name = "Gujarati",
        localName = "ગુજરાતી",
        articleCount = 30722,
        pageCount = 134683,
        activeUserCount = 161
    ),
    SupportedLanguage(
        code = "guw",
        name = "Gun",
        localName = "gungbe",
        articleCount = 1562,
        pageCount = 2778,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "ht",
        name = "Haitian Creole",
        localName = "Kreyòl ayisyen",
        articleCount = 71369,
        pageCount = 92855,
        activeUserCount = 104
    ),
    SupportedLanguage(
        code = "hak",
        name = "Hakka Chinese",
        localName = "客家語 / Hak-kâ-ngî",
        articleCount = 10386,
        pageCount = 19923,
        activeUserCount = 50
    ),
    SupportedLanguage(
        code = "ha",
        name = "Hausa",
        localName = "Hausa",
        articleCount = 72785,
        pageCount = 111251,
        activeUserCount = 245
    ),
    SupportedLanguage(
        code = "haw",
        name = "Hawaiian",
        localName = "Hawaiʻi",
        articleCount = 2966,
        pageCount = 6205,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "he",
        name = "Hebrew",
        localName = "עברית",
        articleCount = 386670,
        pageCount = 1627208,
        activeUserCount = 8432
    ),
    SupportedLanguage(
        code = "hi",
        name = "Hindi",
        localName = "हिन्दी",
        articleCount = 166827,
        pageCount = 1382438,
        activeUserCount = 1275
    ),
    SupportedLanguage(
        code = "hu",
        name = "Hungarian",
        localName = "magyar",
        articleCount = 563438,
        pageCount = 1601905,
        activeUserCount = 2582
    ),
    SupportedLanguage(
        code = "iba",
        name = "Iban",
        localName = "Jaku Iban",
        articleCount = 1857,
        pageCount = 4972,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "is",
        name = "Icelandic",
        localName = "íslenska",
        articleCount = 60851,
        pageCount = 158842,
        activeUserCount = 325
    ),
    SupportedLanguage(
        code = "io",
        name = "Ido",
        localName = "Ido",
        articleCount = 60144,
        pageCount = 87677,
        activeUserCount = 81
    ),
    SupportedLanguage(
        code = "igl",
        name = "Igala",
        localName = "Igala",
        articleCount = 949,
        pageCount = 1349,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "ig",
        name = "Igbo",
        localName = "Igbo",
        articleCount = 44072,
        pageCount = 60527,
        activeUserCount = 108
    ),
    SupportedLanguage(
        code = "ilo",
        name = "Iloko",
        localName = "Ilokano",
        articleCount = 15439,
        pageCount = 70544,
        activeUserCount = 46
    ),
    SupportedLanguage(
        code = "smn",
        name = "Inari Sami",
        localName = "anarâškielâ",
        articleCount = 6493,
        pageCount = 28328,
        activeUserCount = 27
    ),
    SupportedLanguage(
        code = "id",
        name = "Indonesian",
        localName = "Bahasa Indonesia",
        articleCount = 756502,
        pageCount = 4181050,
        activeUserCount = 6732
    ),
    SupportedLanguage(
        code = "inh",
        name = "Ingush",
        localName = "гӀалгӀай",
        articleCount = 2412,
        pageCount = 16087,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "ia",
        name = "Interlingua",
        localName = "interlingua",
        articleCount = 30151,
        pageCount = 45825,
        activeUserCount = 55
    ),
    SupportedLanguage(
        code = "ie",
        name = "Interlingue",
        localName = "Interlingue",
        articleCount = 13387,
        pageCount = 17673,
        activeUserCount = 45
    ),
    SupportedLanguage(
        code = "iu",
        name = "Inuktitut",
        localName = "ᐃᓄᒃᑎᑐᑦ / inuktitut",
        articleCount = 430,
        pageCount = 3020,
        activeUserCount = 41
    ),
    SupportedLanguage(
        code = "ik",
        name = "Inupiaq",
        localName = "Iñupiatun",
        articleCount = 604,
        pageCount = 2873,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "ga",
        name = "Irish",
        localName = "Gaeilge",
        articleCount = 62664,
        pageCount = 112206,
        activeUserCount = 148
    ),
    SupportedLanguage(
        code = "jam",
        name = "Jamaican Creole English",
        localName = "Patois",
        articleCount = 1730,
        pageCount = 3132,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "jv",
        name = "Javanese",
        localName = "Jawa",
        articleCount = 74726,
        pageCount = 185531,
        activeUserCount = 233
    ),
    SupportedLanguage(
        code = "kbd",
        name = "Kabardian",
        localName = "адыгэбзэ",
        articleCount = 1637,
        pageCount = 7101,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "kbp",
        name = "Kabiye",
        localName = "Kabɩyɛ",
        articleCount = 1715,
        pageCount = 3449,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "kab",
        name = "Kabyle",
        localName = "Taqbaylit",
        articleCount = 7007,
        pageCount = 17982,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "kl",
        name = "Kalaallisut",
        localName = "kalaallisut",
        articleCount = 245,
        pageCount = 2292,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "xal",
        name = "Kalmyk",
        localName = "хальмг",
        articleCount = 1596,
        pageCount = 12367,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "kn",
        name = "Kannada",
        localName = "ಕನ್ನಡ",
        articleCount = 34075,
        pageCount = 157745,
        activeUserCount = 296
    ),
    SupportedLanguage(
        code = "kaa",
        name = "Kara-Kalpak",
        localName = "Qaraqalpaqsha",
        articleCount = 10889,
        pageCount = 28763,
        activeUserCount = 55
    ),
    SupportedLanguage(
        code = "krc",
        name = "Karachay-Balkar",
        localName = "къарачай-малкъар",
        articleCount = 2660,
        pageCount = 16821,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "ks",
        name = "Kashmiri",
        localName = "کٲشُر",
        articleCount = 8338,
        pageCount = 20446,
        activeUserCount = 42
    ),
    SupportedLanguage(
        code = "csb",
        name = "Kashubian",
        localName = "kaszëbsczi",
        articleCount = 5498,
        pageCount = 8899,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "kk",
        name = "Kazakh",
        localName = "қазақша",
        articleCount = 241242,
        pageCount = 656983,
        activeUserCount = 417
    ),
    SupportedLanguage(
        code = "km",
        name = "Khmer",
        localName = "ភាសាខ្មែរ",
        articleCount = 11821,
        pageCount = 36785,
        activeUserCount = 168
    ),
    SupportedLanguage(
        code = "ki",
        name = "Kikuyu",
        localName = "Gĩkũyũ",
        articleCount = 1910,
        pageCount = 3798,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "rw",
        name = "Kinyarwanda",
        localName = "Ikinyarwanda",
        articleCount = 8886,
        pageCount = 17684,
        activeUserCount = 80
    ),
    SupportedLanguage(
        code = "kge",
        name = "Komering",
        localName = "Kumoring",
        articleCount = 2660,
        pageCount = 4373,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "kv",
        name = "Komi",
        localName = "коми",
        articleCount = 5731,
        pageCount = 19788,
        activeUserCount = 27
    ),
    SupportedLanguage(
        code = "koi",
        name = "Komi-Permyak",
        localName = "перем коми",
        articleCount = 3468,
        pageCount = 13433,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "kg",
        name = "Kongo",
        localName = "Kongo",
        articleCount = 1570,
        pageCount = 4025,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "ko",
        name = "Korean",
        localName = "한국어",
        articleCount = 730930,
        pageCount = 3483281,
        activeUserCount = 7092
    ),
    SupportedLanguage(
        code = "avk",
        name = "Kotava",
        localName = "Kotava",
        articleCount = 29900,
        pageCount = 36349,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "ku",
        name = "Kurdish",
        localName = "kurdî",
        articleCount = 90927,
        pageCount = 289309,
        activeUserCount = 187
    ),
    SupportedLanguage(
        code = "kus",
        name = "Kusaal",
        localName = "Kʋsaal",
        articleCount = 1224,
        pageCount = 1815,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "ky",
        name = "Kyrgyz",
        localName = "кыргызча",
        articleCount = 76105,
        pageCount = 110222,
        activeUserCount = 127
    ),
    SupportedLanguage(
        code = "lld",
        name = "Ladin",
        localName = "Ladin",
        articleCount = 180830,
        pageCount = 188184,
        activeUserCount = 52
    ),
    SupportedLanguage(
        code = "lad",
        name = "Ladino",
        localName = "Ladino",
        articleCount = 3887,
        pageCount = 13632,
        activeUserCount = 36
    ),
    SupportedLanguage(
        code = "lbe",
        name = "Lak",
        localName = "лакку",
        articleCount = 1251,
        pageCount = 16229,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "lo",
        name = "Lao",
        localName = "ລາວ",
        articleCount = 5227,
        pageCount = 15734,
        activeUserCount = 58
    ),
    SupportedLanguage(
        code = "ltg",
        name = "Latgalian",
        localName = "latgaļu",
        articleCount = 1112,
        pageCount = 3409,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "la",
        name = "Latin",
        localName = "Latina",
        articleCount = 140768,
        pageCount = 291062,
        activeUserCount = 230
    ),
    SupportedLanguage(
        code = "lv",
        name = "Latvian",
        localName = "latviešu",
        articleCount = 138915,
        pageCount = 556378,
        activeUserCount = 715
    ),
    SupportedLanguage(
        code = "lez",
        name = "Lezghian",
        localName = "лезги",
        articleCount = 4452,
        pageCount = 14934,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "lij",
        name = "Ligurian",
        localName = "Ligure",
        articleCount = 11455,
        pageCount = 28114,
        activeUserCount = 46
    ),
    SupportedLanguage(
        code = "li",
        name = "Limburgish",
        localName = "Limburgs",
        articleCount = 15145,
        pageCount = 68665,
        activeUserCount = 44
    ),
    SupportedLanguage(
        code = "ln",
        name = "Lingala",
        localName = "lingála",
        articleCount = 4840,
        pageCount = 11469,
        activeUserCount = 27
    ),
    SupportedLanguage(
        code = "lfn",
        name = "Lingua Franca Nova",
        localName = "Lingua Franca Nova",
        articleCount = 4498,
        pageCount = 7201,
        activeUserCount = 37
    ),
    SupportedLanguage(
        code = "zh-classical",
        name = "Literary Chinese",
        localName = "文言",
        articleCount = 13885,
        pageCount = 117018,
        activeUserCount = 75
    ),
    SupportedLanguage(
        code = "lt",
        name = "Lithuanian",
        localName = "lietuvių",
        articleCount = 224282,
        pageCount = 559894,
        activeUserCount = 829
    ),
    SupportedLanguage(
        code = "olo",
        name = "Livvi-Karelian",
        localName = "livvinkarjala",
        articleCount = 4639,
        pageCount = 13989,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "jbo",
        name = "Lojban",
        localName = "la .lojban.",
        articleCount = 1348,
        pageCount = 5817,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "lmo",
        name = "Lombard",
        localName = "lombard",
        articleCount = 79774,
        pageCount = 152592,
        activeUserCount = 78
    ),
    SupportedLanguage(
        code = "nds",
        name = "Low German",
        localName = "Plattdüütsch",
        articleCount = 85771,
        pageCount = 185080,
        activeUserCount = 66
    ),
    SupportedLanguage(
        code = "nds-nl",
        name = "Low Saxon",
        localName = "Nedersaksies",
        articleCount = 8061,
        pageCount = 21947,
        activeUserCount = 50
    ),
    SupportedLanguage(
        code = "dsb",
        name = "Lower Sorbian",
        localName = "dolnoserbski",
        articleCount = 3427,
        pageCount = 11631,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "lb",
        name = "Luxembourgish",
        localName = "Lëtzebuergesch",
        articleCount = 66106,
        pageCount = 143962,
        activeUserCount = 155
    ),
    SupportedLanguage(
        code = "mk",
        name = "Macedonian",
        localName = "македонски",
        articleCount = 156888,
        pageCount = 589902,
        activeUserCount = 448
    ),
    SupportedLanguage(
        code = "mad",
        name = "Madurese",
        localName = "Madhurâ",
        articleCount = 1997,
        pageCount = 11305,
        activeUserCount = 44
    ),
    SupportedLanguage(
        code = "mai",
        name = "Maithili",
        localName = "मैथिली",
        articleCount = 14252,
        pageCount = 45283,
        activeUserCount = 34
    ),
    SupportedLanguage(
        code = "mg",
        name = "Malagasy",
        localName = "Malagasy",
        articleCount = 101420,
        pageCount = 258869,
        activeUserCount = 74
    ),
    SupportedLanguage(
        code = "ms",
        name = "Malay",
        localName = "Bahasa Melayu",
        articleCount = 434788,
        pageCount = 1168463,
        activeUserCount = 2810
    ),
    SupportedLanguage(
        code = "ml",
        name = "Malayalam",
        localName = "മലയാളം",
        articleCount = 87417,
        pageCount = 548363,
        activeUserCount = 568
    ),
    SupportedLanguage(
        code = "mt",
        name = "Maltese",
        localName = "Malti",
        articleCount = 7665,
        pageCount = 23532,
        activeUserCount = 78
    ),
    SupportedLanguage(
        code = "mni",
        name = "Manipuri",
        localName = "ꯃꯤꯇꯩ ꯂꯣꯟ",
        articleCount = 10454,
        pageCount = 17375,
        activeUserCount = 34
    ),
    SupportedLanguage(
        code = "gv",
        name = "Manx",
        localName = "Gaelg",
        articleCount = 7040,
        pageCount = 39218,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "mr",
        name = "Marathi",
        localName = "मराठी",
        articleCount = 100803,
        pageCount = 326143,
        activeUserCount = 393
    ),
    SupportedLanguage(
        code = "mzn",
        name = "Mazanderani",
        localName = "مازِرونی",
        articleCount = 64361,
        pageCount = 106988,
        activeUserCount = 56
    ),
    SupportedLanguage(
        code = "min",
        name = "Minangkabau",
        localName = "Minangkabau",
        articleCount = 228709,
        pageCount = 473765,
        activeUserCount = 102
    ),
    SupportedLanguage(
        code = "cdo",
        name = "Mindong",
        localName = "閩東語 / Mìng-dĕ̤ng-ngṳ̄",
        articleCount = 16692,
        pageCount = 33553,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "xmf",
        name = "Mingrelian",
        localName = "მარგალური",
        articleCount = 21937,
        pageCount = 41544,
        activeUserCount = 36
    ),
    SupportedLanguage(
        code = "zh-min-nan",
        name = "Minnan",
        localName = "閩南語 / Bân-lâm-gí",
        articleCount = 433849,
        pageCount = 1074907,
        activeUserCount = 135
    ),
    SupportedLanguage(
        code = "mwl",
        name = "Mirandese",
        localName = "Mirandés",
        articleCount = 4283,
        pageCount = 10737,
        activeUserCount = 27
    ),
    SupportedLanguage(
        code = "mdf",
        name = "Moksha",
        localName = "мокшень",
        articleCount = 7614,
        pageCount = 23715,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "mnw",
        name = "Mon",
        localName = "ဘာသာမန်",
        articleCount = 1963,
        pageCount = 6794,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "mn",
        name = "Mongolian",
        localName = "монгол",
        articleCount = 26519,
        pageCount = 113391,
        activeUserCount = 288
    ),
    SupportedLanguage(
        code = "ary",
        name = "Moroccan Arabic",
        localName = "الدارجة",
        articleCount = 10920,
        pageCount = 90781,
        activeUserCount = 54
    ),
    SupportedLanguage(
        code = "mos",
        name = "Mossi",
        localName = "moore",
        articleCount = 1306,
        pageCount = 2089,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "mi",
        name = "Māori",
        localName = "Māori",
        articleCount = 8019,
        pageCount = 15443,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "nah",
        name = "Nahuatl",
        localName = "Nāhuatl",
        articleCount = 4282,
        pageCount = 13348,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "nv",
        name = "Navajo",
        localName = "Diné bizaad",
        articleCount = 22664,
        pageCount = 37321,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "nap",
        name = "Neapolitan",
        localName = "Napulitano",
        articleCount = 14937,
        pageCount = 24158,
        activeUserCount = 38
    ),
    SupportedLanguage(
        code = "ne",
        name = "Nepali",
        localName = "नेपाली",
        articleCount = 29343,
        pageCount = 112063,
        activeUserCount = 205
    ),
    SupportedLanguage(
        code = "new",
        name = "Newari",
        localName = "नेपाल भाषा",
        articleCount = 72648,
        pageCount = 166753,
        activeUserCount = 27
    ),
    SupportedLanguage(
        code = "nia",
        name = "Nias",
        localName = "Li Niha",
        articleCount = 1766,
        pageCount = 4462,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "pcm",
        name = "Nigerian Pidgin",
        localName = "Naijá",
        articleCount = 1524,
        pageCount = 2782,
        activeUserCount = 20
    ),
    SupportedLanguage(
        code = "nrm",
        name = "Norman",
        localName = "Nouormand",
        articleCount = 5055,
        pageCount = 10682,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "frr",
        name = "Northern Frisian",
        localName = "Nordfriisk",
        articleCount = 20688,
        pageCount = 51306,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "se",
        name = "Northern Sami",
        localName = "davvisámegiella",
        articleCount = 7905,
        pageCount = 21129,
        activeUserCount = 29
    ),
    SupportedLanguage(
        code = "nso",
        name = "Northern Sotho",
        localName = "Sesotho sa Leboa",
        articleCount = 8783,
        pageCount = 11493,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "no",
        name = "Norwegian",
        localName = "norsk",
        articleCount = 661788,
        pageCount = 1881561,
        activeUserCount = 2965
    ),
    SupportedLanguage(
        code = "nn",
        name = "Norwegian Nynorsk",
        localName = "norsk nynorsk",
        articleCount = 176906,
        pageCount = 398476,
        activeUserCount = 205
    ),
    SupportedLanguage(
        code = "nov",
        name = "Novial",
        localName = "Novial",
        articleCount = 1887,
        pageCount = 4823,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "nup",
        name = "Nupe",
        localName = "Nupe",
        articleCount = 532,
        pageCount = 1041,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "ny",
        name = "Nyanja",
        localName = "Chi-Chewa",
        articleCount = 1099,
        pageCount = 5416,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "nqo",
        name = "N’Ko",
        localName = "ߒߞߏ",
        articleCount = 1581,
        pageCount = 3416,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "ann",
        name = "Obolo",
        localName = "Obolo",
        articleCount = 433,
        pageCount = 893,
        activeUserCount = 11
    ),
    SupportedLanguage(
        code = "oc",
        name = "Occitan",
        localName = "occitan",
        articleCount = 90411,
        pageCount = 166429,
        activeUserCount = 144
    ),
    SupportedLanguage(
        code = "or",
        name = "Odia",
        localName = "ଓଡ଼ିଆ",
        articleCount = 20080,
        pageCount = 85473,
        activeUserCount = 97
    ),
    SupportedLanguage(
        code = "ang",
        name = "Old English",
        localName = "Ænglisc",
        articleCount = 5026,
        pageCount = 21210,
        activeUserCount = 82
    ),
    SupportedLanguage(
        code = "om",
        name = "Oromo",
        localName = "Oromoo",
        articleCount = 1962,
        pageCount = 5342,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "os",
        name = "Ossetic",
        localName = "ирон",
        articleCount = 21387,
        pageCount = 76504,
        activeUserCount = 40
    ),
    SupportedLanguage(
        code = "blk",
        name = "Pa'O",
        localName = "ပအိုဝ်ႏဘာႏသာႏ",
        articleCount = 2907,
        pageCount = 8704,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "pwn",
        name = "Paiwan",
        localName = "pinayuanan",
        articleCount = 376,
        pageCount = 634,
        activeUserCount = 10
    ),
    SupportedLanguage(
        code = "pfl",
        name = "Palatine German",
        localName = "Pälzisch",
        articleCount = 2831,
        pageCount = 7071,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "pi",
        name = "Pali",
        localName = "पालि",
        articleCount = 290,
        pageCount = 1827,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "pam",
        name = "Pampanga",
        localName = "Kapampangan",
        articleCount = 10138,
        pageCount = 23253,
        activeUserCount = 37
    ),
    SupportedLanguage(
        code = "pag",
        name = "Pangasinan",
        localName = "Pangasinan",
        articleCount = 2618,
        pageCount = 6740,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "rsk",
        name = "Pannonian Rusyn",
        localName = "руски",
        articleCount = 1003,
        pageCount = 2218,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "pap",
        name = "Papiamento",
        localName = "Papiamentu",
        articleCount = 5007,
        pageCount = 10396,
        activeUserCount = 27
    ),
    SupportedLanguage(
        code = "ps",
        name = "Pashto",
        localName = "پښتو",
        articleCount = 20831,
        pageCount = 74967,
        activeUserCount = 74
    ),
    SupportedLanguage(
        code = "pdc",
        name = "Pennsylvania German",
        localName = "Deitsch",
        articleCount = 2042,
        pageCount = 6048,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "fa",
        name = "Persian",
        localName = "فارسی",
        articleCount = 1063445,
        pageCount = 6002873,
        activeUserCount = 11465
    ),
    SupportedLanguage(
        code = "pcd",
        name = "Picard",
        localName = "Picard",
        articleCount = 6036,
        pageCount = 12026,
        activeUserCount = 42
    ),
    SupportedLanguage(
        code = "pms",
        name = "Piedmontese",
        localName = "Piemontèis",
        articleCount = 70691,
        pageCount = 106716,
        activeUserCount = 50
    ),
    SupportedLanguage(
        code = "pnt",
        name = "Pontic",
        localName = "Ποντιακά",
        articleCount = 489,
        pageCount = 2107,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "pt",
        name = "Portuguese",
        localName = "português",
        articleCount = 1161120,
        pageCount = 6011665,
        activeUserCount = 8403
    ),
    SupportedLanguage(
        code = "pa",
        name = "Punjabi",
        localName = "ਪੰਜਾਬੀ",
        articleCount = 59219,
        pageCount = 190316,
        activeUserCount = 173
    ),
    SupportedLanguage(
        code = "qu",
        name = "Quechua",
        localName = "Runa Simi",
        articleCount = 24301,
        pageCount = 58401,
        activeUserCount = 55
    ),
    SupportedLanguage(
        code = "ro",
        name = "Romanian",
        localName = "română",
        articleCount = 518520,
        pageCount = 2936402,
        activeUserCount = 2362
    ),
    SupportedLanguage(
        code = "rm",
        name = "Romansh",
        localName = "rumantsch",
        articleCount = 3813,
        pageCount = 9784,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "rn",
        name = "Rundi",
        localName = "ikirundi",
        articleCount = 703,
        pageCount = 2728,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "bxr",
        name = "Russia Buriat",
        localName = "буряад",
        articleCount = 2911,
        pageCount = 11322,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "rue",
        name = "Rusyn",
        localName = "русиньскый",
        articleCount = 10143,
        pageCount = 22320,
        activeUserCount = 48
    ),
    SupportedLanguage(
        code = "szy",
        name = "Sakizaya",
        localName = "Sakizaya",
        articleCount = 2735,
        pageCount = 6314,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "sm",
        name = "Samoan",
        localName = "Gagana Samoa",
        articleCount = 1198,
        pageCount = 6171,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "bat-smg",
        name = "Samogitian",
        localName = "žemaitėška",
        articleCount = 17274,
        pageCount = 30066,
        activeUserCount = 33
    ),
    SupportedLanguage(
        code = "sg",
        name = "Sango",
        localName = "Sängö",
        articleCount = 369,
        pageCount = 2064,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "sa",
        name = "Sanskrit",
        localName = "संस्कृतम्",
        articleCount = 12418,
        pageCount = 81274,
        activeUserCount = 69
    ),
    SupportedLanguage(
        code = "sat",
        name = "Santali",
        localName = "ᱥᱟᱱᱛᱟᱲᱤ",
        articleCount = 13989,
        pageCount = 29933,
        activeUserCount = 55
    ),
    SupportedLanguage(
        code = "skr",
        name = "Saraiki",
        localName = "سرائیکی",
        articleCount = 24336,
        pageCount = 28726,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "sc",
        name = "Sardinian",
        localName = "sardu",
        articleCount = 7735,
        pageCount = 17537,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "stq",
        name = "Saterland Frisian",
        localName = "Seeltersk",
        articleCount = 4129,
        pageCount = 10819,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "sco",
        name = "Scots",
        localName = "Scots",
        articleCount = 34283,
        pageCount = 138198,
        activeUserCount = 144
    ),
    SupportedLanguage(
        code = "gd",
        name = "Scottish Gaelic",
        localName = "Gàidhlig",
        articleCount = 16017,
        pageCount = 32591,
        activeUserCount = 37
    ),
    SupportedLanguage(
        code = "sr",
        name = "Serbian",
        localName = "српски / srpski",
        articleCount = 713211,
        pageCount = 4218375,
        activeUserCount = 2198
    ),
    SupportedLanguage(
        code = "sh",
        name = "Serbo-Croatian",
        localName = "srpskohrvatski / српскохрватски",
        articleCount = 461215,
        pageCount = 4627175,
        activeUserCount = 410
    ),
    SupportedLanguage(
        code = "shn",
        name = "Shan",
        localName = "တႆး",
        articleCount = 14343,
        pageCount = 34244,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "sn",
        name = "Shona",
        localName = "chiShona",
        articleCount = 11497,
        pageCount = 20499,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "scn",
        name = "Sicilian",
        localName = "sicilianu",
        articleCount = 26263,
        pageCount = 56029,
        activeUserCount = 69
    ),
    SupportedLanguage(
        code = "szl",
        name = "Silesian",
        localName = "ślůnski",
        articleCount = 59586,
        pageCount = 75971,
        activeUserCount = 49
    ),
    SupportedLanguage(
        code = "simple",
        name = "Simple English",
        localName = "Simple English",
        articleCount = 276804,
        pageCount = 918051,
        activeUserCount = 5098
    ),
    SupportedLanguage(
        code = "sd",
        name = "Sindhi",
        localName = "سنڌي",
        articleCount = 19678,
        pageCount = 70555,
        activeUserCount = 47
    ),
    SupportedLanguage(
        code = "si",
        name = "Sinhala",
        localName = "සිංහල",
        articleCount = 24816,
        pageCount = 166298,
        activeUserCount = 201
    ),
    SupportedLanguage(
        code = "sk",
        name = "Slovak",
        localName = "slovenčina",
        articleCount = 256869,
        pageCount = 595608,
        activeUserCount = 1539
    ),
    SupportedLanguage(
        code = "sl",
        name = "Slovenian",
        localName = "slovenščina",
        articleCount = 195756,
        pageCount = 499283,
        activeUserCount = 789
    ),
    SupportedLanguage(
        code = "so",
        name = "Somali",
        localName = "Soomaaliga",
        articleCount = 9351,
        pageCount = 29137,
        activeUserCount = 194
    ),
    SupportedLanguage(
        code = "azb",
        name = "South Azerbaijani",
        localName = "تۆرکجه",
        articleCount = 244417,
        pageCount = 579861,
        activeUserCount = 126
    ),
    SupportedLanguage(
        code = "nr",
        name = "South Ndebele",
        localName = "isiNdebele seSewula",
        articleCount = 274,
        pageCount = 857,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "alt",
        name = "Southern Altai",
        localName = "алтай тил",
        articleCount = 1102,
        pageCount = 7009,
        activeUserCount = 11
    ),
    SupportedLanguage(
        code = "dga",
        name = "Southern Dagaare",
        localName = "Dagaare",
        articleCount = 2953,
        pageCount = 6474,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "st",
        name = "Southern Sotho",
        localName = "Sesotho",
        articleCount = 1546,
        pageCount = 5421,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "srn",
        name = "Sranan Tongo",
        localName = "Sranantongo",
        articleCount = 1128,
        pageCount = 2730,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "zgh",
        name = "Standard Moroccan Tamazight",
        localName = "ⵜⴰⵎⴰⵣⵉⵖⵜ ⵜⴰⵏⴰⵡⴰⵢⵜ",
        articleCount = 11959,
        pageCount = 39701,
        activeUserCount = 88
    ),
    SupportedLanguage(
        code = "su",
        name = "Sundanese",
        localName = "Sunda",
        articleCount = 62154,
        pageCount = 99751,
        activeUserCount = 73
    ),
    SupportedLanguage(
        code = "sw",
        name = "Swahili",
        localName = "Kiswahili",
        articleCount = 102753,
        pageCount = 206919,
        activeUserCount = 375
    ),
    SupportedLanguage(
        code = "ss",
        name = "Swati",
        localName = "SiSwati",
        articleCount = 1133,
        pageCount = 3484,
        activeUserCount = 20
    ),
    SupportedLanguage(
        code = "syl",
        name = "Sylheti",
        localName = "ꠍꠤꠟꠐꠤ",
        articleCount = 1195,
        pageCount = 6212,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "shi",
        name = "Tachelhit",
        localName = "Taclḥit",
        articleCount = 10882,
        pageCount = 14941,
        activeUserCount = 32
    ),
    SupportedLanguage(
        code = "tl",
        name = "Tagalog",
        localName = "Tagalog",
        articleCount = 48787,
        pageCount = 247967,
        activeUserCount = 343
    ),
    SupportedLanguage(
        code = "ty",
        name = "Tahitian",
        localName = "reo tahiti",
        articleCount = 1250,
        pageCount = 3077,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "tdd",
        name = "Tai Nuea",
        localName = "ᥖᥭᥰ ᥖᥬᥲ ᥑᥨᥒᥰ",
        articleCount = 448,
        pageCount = 2193,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "tg",
        name = "Tajik",
        localName = "тоҷикӣ",
        articleCount = 116059,
        pageCount = 283763,
        activeUserCount = 128
    ),
    SupportedLanguage(
        code = "tly",
        name = "Talysh",
        localName = "tolışi",
        articleCount = 10049,
        pageCount = 13843,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "ta",
        name = "Tamil",
        localName = "தமிழ்",
        articleCount = 178783,
        pageCount = 608965,
        activeUserCount = 617
    ),
    SupportedLanguage(
        code = "roa-tara",
        name = "Tarantino",
        localName = "tarandíne",
        articleCount = 9497,
        pageCount = 18906,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "trv",
        name = "Taroko",
        localName = "Seediq",
        articleCount = 1201,
        pageCount = 2210,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "tt",
        name = "Tatar",
        localName = "татарча / tatarça",
        articleCount = 562142,
        pageCount = 899663,
        activeUserCount = 115
    ),
    SupportedLanguage(
        code = "te",
        name = "Telugu",
        localName = "తెలుగు",
        articleCount = 117202,
        pageCount = 400632,
        activeUserCount = 415
    ),
    SupportedLanguage(
        code = "tet",
        name = "Tetum",
        localName = "tetun",
        articleCount = 1380,
        pageCount = 3953,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "th",
        name = "Thai",
        localName = "ไทย",
        articleCount = 177896,
        pageCount = 1146465,
        activeUserCount = 3623
    ),
    SupportedLanguage(
        code = "bo",
        name = "Tibetan",
        localName = "བོད་ཡིག",
        articleCount = 7423,
        pageCount = 21043,
        activeUserCount = 59
    ),
    SupportedLanguage(
        code = "tig",
        name = "Tigre",
        localName = "ትግሬ",
        articleCount = 41,
        pageCount = 482,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "ti",
        name = "Tigrinya",
        localName = "ትግርኛ",
        articleCount = 336,
        pageCount = 3032,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "tpi",
        name = "Tok Pisin",
        localName = "Tok Pisin",
        articleCount = 1406,
        pageCount = 5795,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "tok",
        name = "Toki Pona",
        localName = "toki pona",
        articleCount = 3264,
        pageCount = 8477,
        activeUserCount = 60
    ),
    SupportedLanguage(
        code = "to",
        name = "Tongan",
        localName = "lea faka-Tonga",
        articleCount = 2043,
        pageCount = 5536,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "ts",
        name = "Tsonga",
        localName = "Xitsonga",
        articleCount = 957,
        pageCount = 4256,
        activeUserCount = 27
    ),
    SupportedLanguage(
        code = "tn",
        name = "Tswana",
        localName = "Setswana",
        articleCount = 3491,
        pageCount = 7726,
        activeUserCount = 36
    ),
    SupportedLanguage(
        code = "tcy",
        name = "Tulu",
        localName = "ತುಳು",
        articleCount = 2895,
        pageCount = 17313,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "tum",
        name = "Tumbuka",
        localName = "chiTumbuka",
        articleCount = 18796,
        pageCount = 39192,
        activeUserCount = 33
    ),
    SupportedLanguage(
        code = "tr",
        name = "Turkish",
        localName = "Türkçe",
        articleCount = 653665,
        pageCount = 3381195,
        activeUserCount = 5353
    ),
    SupportedLanguage(
        code = "tk",
        name = "Turkmen",
        localName = "Türkmençe",
        articleCount = 7048,
        pageCount = 17533,
        activeUserCount = 85
    ),
    SupportedLanguage(
        code = "tyv",
        name = "Tuvinian",
        localName = "тыва дыл",
        articleCount = 4050,
        pageCount = 14511,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "tw",
        name = "Twi",
        localName = "Twi",
        articleCount = 4624,
        pageCount = 8592,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "kcg",
        name = "Tyap",
        localName = "Tyap",
        articleCount = 1463,
        pageCount = 6416,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "udm",
        name = "Udmurt",
        localName = "удмурт",
        articleCount = 5714,
        pageCount = 20052,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "hsb",
        name = "Upper Sorbian",
        localName = "hornjoserbsce",
        articleCount = 14211,
        pageCount = 36374,
        activeUserCount = 40
    ),
    SupportedLanguage(
        code = "ur",
        name = "Urdu",
        localName = "اردو",
        articleCount = 235661,
        pageCount = 2303557,
        activeUserCount = 386
    ),
    SupportedLanguage(
        code = "ug",
        name = "Uyghur",
        localName = "ئۇيغۇرچە / Uyghurche",
        articleCount = 9608,
        pageCount = 16994,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "uz",
        name = "Uzbek",
        localName = "oʻzbekcha / ўзбекча",
        articleCount = 317369,
        pageCount = 1165403,
        activeUserCount = 916
    ),
    SupportedLanguage(
        code = "ve",
        name = "Venda",
        localName = "Tshivenda",
        articleCount = 822,
        pageCount = 2419,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "vec",
        name = "Venetian",
        localName = "vèneto",
        articleCount = 69528,
        pageCount = 143315,
        activeUserCount = 61
    ),
    SupportedLanguage(
        code = "vep",
        name = "Veps",
        localName = "vepsän kel’",
        articleCount = 7077,
        pageCount = 38124,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "rmy",
        name = "Vlax Romani",
        localName = "romani čhib",
        articleCount = 756,
        pageCount = 2841,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "vo",
        name = "Volapük",
        localName = "Volapük",
        articleCount = 46290,
        pageCount = 163969,
        activeUserCount = 37
    ),
    SupportedLanguage(
        code = "fiu-vro",
        name = "Võro",
        localName = "võro",
        articleCount = 6871,
        pageCount = 13038,
        activeUserCount = 47
    ),
    SupportedLanguage(
        code = "wa",
        name = "Walloon",
        localName = "walon",
        articleCount = 12807,
        pageCount = 30665,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "war",
        name = "Waray",
        localName = "Winaray",
        articleCount = 1266828,
        pageCount = 2870613,
        activeUserCount = 92
    ),
    SupportedLanguage(
        code = "guc",
        name = "Wayuu",
        localName = "wayuunaiki",
        articleCount = 687,
        pageCount = 1350,
        activeUserCount = 39
    ),
    SupportedLanguage(
        code = "cy",
        name = "Welsh",
        localName = "Cymraeg",
        articleCount = 283800,
        pageCount = 535133,
        activeUserCount = 178
    ),
    SupportedLanguage(
        code = "bdr",
        name = "West Coast Bajau",
        localName = "Bajau Sama",
        articleCount = 236,
        pageCount = 1319,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "vls",
        name = "West Flemish",
        localName = "West-Vlams",
        articleCount = 8221,
        pageCount = 22450,
        activeUserCount = 49
    ),
    SupportedLanguage(
        code = "hyw",
        name = "Western Armenian",
        localName = "Արեւմտահայերէն",
        articleCount = 13270,
        pageCount = 28770,
        activeUserCount = 43
    ),
    SupportedLanguage(
        code = "fy",
        name = "Western Frisian",
        localName = "Frysk",
        articleCount = 58637,
        pageCount = 175800,
        activeUserCount = 99
    ),
    SupportedLanguage(
        code = "mrj",
        name = "Western Mari",
        localName = "кырык мары",
        articleCount = 10429,
        pageCount = 21162,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "pnb",
        name = "Western Punjabi",
        localName = "پنجابی",
        articleCount = 74789,
        pageCount = 140527,
        activeUserCount = 64
    ),
    SupportedLanguage(
        code = "wo",
        name = "Wolof",
        localName = "Wolof",
        articleCount = 1743,
        pageCount = 5567,
        activeUserCount = 29
    ),
    SupportedLanguage(
        code = "wuu",
        name = "Wu",
        localName = "吴语",
        articleCount = 46995,
        pageCount = 71529,
        activeUserCount = 110
    ),
    SupportedLanguage(
        code = "xh",
        name = "Xhosa",
        localName = "isiXhosa",
        articleCount = 2319,
        pageCount = 5574,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "sah",
        name = "Yakut",
        localName = "саха тыла",
        articleCount = 17808,
        pageCount = 53895,
        activeUserCount = 56
    ),
    SupportedLanguage(
        code = "yi",
        name = "Yiddish",
        localName = "ייִדיש",
        articleCount = 15638,
        pageCount = 44416,
        activeUserCount = 54
    ),
    SupportedLanguage(
        code = "yo",
        name = "Yoruba",
        localName = "Yorùbá",
        articleCount = 36339,
        pageCount = 61095,
        activeUserCount = 103
    ),
    SupportedLanguage(
        code = "zea",
        name = "Zeelandic",
        localName = "Zeêuws",
        articleCount = 7099,
        pageCount = 13557,
        activeUserCount = 34
    ),
    SupportedLanguage(
        code = "za",
        name = "Zhuang",
        localName = "Vahcuengh",
        articleCount = 3005,
        pageCount = 5569,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "zu",
        name = "Zulu",
        localName = "isiZulu",
        articleCount = 11819,
        pageCount = 26153,
        activeUserCount = 54
    ),
)
