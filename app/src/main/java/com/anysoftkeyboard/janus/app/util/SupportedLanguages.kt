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
        articleCount = 7106633,
        pageCount = 64662213,
        activeUserCount = 302746
    ),
    SupportedLanguage(
        code = "ceb",
        name = "Cebuano",
        localName = "Cebuano",
        articleCount = 6115867,
        pageCount = 11230229,
        activeUserCount = 188
    ),
    SupportedLanguage(
        code = "de",
        name = "German",
        localName = "Deutsch",
        articleCount = 3078306,
        pageCount = 8440921,
        activeUserCount = 39341
    ),
    SupportedLanguage(
        code = "fr",
        name = "French",
        localName = "français",
        articleCount = 2726094,
        pageCount = 13773932,
        activeUserCount = 40814
    ),
    SupportedLanguage(
        code = "sv",
        name = "Swedish",
        localName = "svenska",
        articleCount = 2620115,
        pageCount = 6346978,
        activeUserCount = 5127
    ),
    SupportedLanguage(
        code = "nl",
        name = "Dutch",
        localName = "Nederlands",
        articleCount = 2205661,
        pageCount = 4747451,
        activeUserCount = 9176
    ),
    SupportedLanguage(
        code = "es",
        name = "Spanish",
        localName = "español",
        articleCount = 2080831,
        pageCount = 8551723,
        activeUserCount = 42792
    ),
    SupportedLanguage(
        code = "ru",
        name = "Russian",
        localName = "русский",
        articleCount = 2075896,
        pageCount = 8442982,
        activeUserCount = 7628
    ),
    SupportedLanguage(
        code = "it",
        name = "Italian",
        localName = "italiano",
        articleCount = 1948467,
        pageCount = 8512199,
        activeUserCount = 32295
    ),
    SupportedLanguage(
        code = "pl",
        name = "Polish",
        localName = "polski",
        articleCount = 1678149,
        pageCount = 3966812,
        activeUserCount = 11610
    ),
    SupportedLanguage(
        code = "arz",
        name = "Egyptian Arabic",
        localName = "مصرى",
        articleCount = 1629973,
        pageCount = 2210296,
        activeUserCount = 344
    ),
    SupportedLanguage(
        code = "zh",
        name = "Chinese",
        localName = "中文",
        articleCount = 1515539,
        pageCount = 8264560,
        activeUserCount = 16772
    ),
    SupportedLanguage(
        code = "ja",
        name = "Japanese",
        localName = "日本語",
        articleCount = 1483990,
        pageCount = 4359842,
        activeUserCount = 26737
    ),
    SupportedLanguage(
        code = "uk",
        name = "Ukrainian",
        localName = "українська",
        articleCount = 1400177,
        pageCount = 5148934,
        activeUserCount = 5997
    ),
    SupportedLanguage(
        code = "vi",
        name = "Vietnamese",
        localName = "Tiếng Việt",
        articleCount = 1296632,
        pageCount = 11802149,
        activeUserCount = 3775
    ),
    SupportedLanguage(
        code = "ab",
        name = "Abkhazian",
        localName = "аԥсшәа",
        articleCount = 6480,
        pageCount = 32913,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "ace",
        name = "Acehnese",
        localName = "Acèh",
        articleCount = 13004,
        pageCount = 27972,
        activeUserCount = 52
    ),
    SupportedLanguage(
        code = "ady",
        name = "Adyghe",
        localName = "адыгабзэ",
        articleCount = 619,
        pageCount = 4719,
        activeUserCount = 20
    ),
    SupportedLanguage(
        code = "af",
        name = "Afrikaans",
        localName = "Afrikaans",
        articleCount = 127509,
        pageCount = 433957,
        activeUserCount = 312
    ),
    SupportedLanguage(
        code = "sq",
        name = "Albanian",
        localName = "shqip",
        articleCount = 104881,
        pageCount = 321191,
        activeUserCount = 547
    ),
    SupportedLanguage(
        code = "als",
        name = "Alemannic",
        localName = "Alemannisch",
        articleCount = 31417,
        pageCount = 74727,
        activeUserCount = 146
    ),
    SupportedLanguage(
        code = "am",
        name = "Amharic",
        localName = "አማርኛ",
        articleCount = 15460,
        pageCount = 46439,
        activeUserCount = 96
    ),
    SupportedLanguage(
        code = "ami",
        name = "Amis",
        localName = "Pangcah",
        articleCount = 1145,
        pageCount = 2255,
        activeUserCount = 7
    ),
    SupportedLanguage(
        code = "anp",
        name = "Angika",
        localName = "अंगिका",
        articleCount = 1669,
        pageCount = 5281,
        activeUserCount = 10
    ),
    SupportedLanguage(
        code = "ar",
        name = "Arabic",
        localName = "العربية",
        articleCount = 1289618,
        pageCount = 8999985,
        activeUserCount = 6990
    ),
    SupportedLanguage(
        code = "an",
        name = "Aragonese",
        localName = "aragonés",
        articleCount = 50856,
        pageCount = 184641,
        activeUserCount = 102
    ),
    SupportedLanguage(
        code = "rki",
        name = "Arakanese",
        localName = "ရခိုင်",
        articleCount = 1082,
        pageCount = 3230,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "arc",
        name = "Aramaic",
        localName = "ܐܪܡܝܐ",
        articleCount = 1918,
        pageCount = 6577,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "hy",
        name = "Armenian",
        localName = "հայերեն",
        articleCount = 323212,
        pageCount = 1187341,
        activeUserCount = 748
    ),
    SupportedLanguage(
        code = "roa-rup",
        name = "Aromanian",
        localName = "armãneashti",
        articleCount = 1390,
        pageCount = 4624,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "frp",
        name = "Arpitan",
        localName = "arpetan",
        articleCount = 5817,
        pageCount = 18045,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "as",
        name = "Assamese",
        localName = "অসমীয়া",
        articleCount = 20901,
        pageCount = 117730,
        activeUserCount = 207
    ),
    SupportedLanguage(
        code = "ast",
        name = "Asturian",
        localName = "asturianu",
        articleCount = 138369,
        pageCount = 247504,
        activeUserCount = 194
    ),
    SupportedLanguage(
        code = "tay",
        name = "Atayal",
        localName = "Tayal",
        articleCount = 2582,
        pageCount = 3299,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "atj",
        name = "Atikamekw",
        localName = "Atikamekw",
        articleCount = 2077,
        pageCount = 3290,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "av",
        name = "Avaric",
        localName = "авар",
        articleCount = 4004,
        pageCount = 18797,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "awa",
        name = "Awadhi",
        localName = "अवधी",
        articleCount = 2597,
        pageCount = 6706,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "ay",
        name = "Aymara",
        localName = "Aymar aru",
        articleCount = 5243,
        pageCount = 8917,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "az",
        name = "Azerbaijani",
        localName = "azərbaycanca",
        articleCount = 209942,
        pageCount = 640222,
        activeUserCount = 1126
    ),
    SupportedLanguage(
        code = "ban",
        name = "Balinese",
        localName = "Basa Bali",
        articleCount = 34972,
        pageCount = 71420,
        activeUserCount = 117
    ),
    SupportedLanguage(
        code = "bm",
        name = "Bambara",
        localName = "bamanankan",
        articleCount = 920,
        pageCount = 3338,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "bn",
        name = "Bangla",
        localName = "বাংলা",
        articleCount = 178721,
        pageCount = 1372423,
        activeUserCount = 2453
    ),
    SupportedLanguage(
        code = "bjn",
        name = "Banjar",
        localName = "Banjar",
        articleCount = 11488,
        pageCount = 35101,
        activeUserCount = 68
    ),
    SupportedLanguage(
        code = "map-bms",
        name = "Banyumasan",
        localName = "Basa Banyumasan",
        articleCount = 13940,
        pageCount = 30445,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "ba",
        name = "Bashkir",
        localName = "башҡортса",
        articleCount = 63917,
        pageCount = 183925,
        activeUserCount = 82
    ),
    SupportedLanguage(
        code = "eu",
        name = "Basque",
        localName = "euskara",
        articleCount = 477089,
        pageCount = 1023885,
        activeUserCount = 1497
    ),
    SupportedLanguage(
        code = "btm",
        name = "Batak Mandailing",
        localName = "Batak Mandailing",
        articleCount = 1198,
        pageCount = 2685,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "bbc",
        name = "Batak Toba",
        localName = "Batak Toba",
        articleCount = 1318,
        pageCount = 2400,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "bar",
        name = "Bavarian",
        localName = "Boarisch",
        articleCount = 27204,
        pageCount = 110600,
        activeUserCount = 73
    ),
    SupportedLanguage(
        code = "be",
        name = "Belarusian",
        localName = "беларуская",
        articleCount = 257946,
        pageCount = 720904,
        activeUserCount = 387
    ),
    SupportedLanguage(
        code = "be-tarask",
        name = "Belarusian (Taraškievica orthography)",
        localName = "беларуская (тарашкевіца)",
        articleCount = 90160,
        pageCount = 264146,
        activeUserCount = 336
    ),
    SupportedLanguage(
        code = "bew",
        name = "Betawi",
        localName = "Betawi",
        articleCount = 3111,
        pageCount = 7495,
        activeUserCount = 62
    ),
    SupportedLanguage(
        code = "bh",
        name = "Bhojpuri",
        localName = "भोजपुरी",
        articleCount = 8859,
        pageCount = 80689,
        activeUserCount = 48
    ),
    SupportedLanguage(
        code = "bpy",
        name = "Bishnupriya",
        localName = "বিষ্ণুপ্রিয়া মণিপুরী",
        articleCount = 25092,
        pageCount = 63454,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "bi",
        name = "Bislama",
        localName = "Bislama",
        articleCount = 1479,
        pageCount = 3445,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "bs",
        name = "Bosnian",
        localName = "bosanski",
        articleCount = 96312,
        pageCount = 382501,
        activeUserCount = 334
    ),
    SupportedLanguage(
        code = "br",
        name = "Breton",
        localName = "brezhoneg",
        articleCount = 90104,
        pageCount = 160468,
        activeUserCount = 131
    ),
    SupportedLanguage(
        code = "bug",
        name = "Buginese",
        localName = "Basa Ugi",
        articleCount = 15957,
        pageCount = 20474,
        activeUserCount = 10
    ),
    SupportedLanguage(
        code = "bg",
        name = "Bulgarian",
        localName = "български",
        articleCount = 306855,
        pageCount = 694906,
        activeUserCount = 2411
    ),
    SupportedLanguage(
        code = "my",
        name = "Burmese",
        localName = "မြန်မာဘာသာ",
        articleCount = 109846,
        pageCount = 261792,
        activeUserCount = 305
    ),
    SupportedLanguage(
        code = "zh-yue",
        name = "Cantonese",
        localName = "粵語",
        articleCount = 148414,
        pageCount = 335131,
        activeUserCount = 1011
    ),
    SupportedLanguage(
        code = "ca",
        name = "Catalan",
        localName = "català",
        articleCount = 785776,
        pageCount = 1972864,
        activeUserCount = 2961
    ),
    SupportedLanguage(
        code = "bcl",
        name = "Central Bikol",
        localName = "Bikol Central",
        articleCount = 21261,
        pageCount = 50105,
        activeUserCount = 68
    ),
    SupportedLanguage(
        code = "dtp",
        name = "Central Dusun",
        localName = "Kadazandusun",
        articleCount = 1718,
        pageCount = 7240,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "knc",
        name = "Central Kanuri",
        localName = "Yerwa Kanuri",
        articleCount = 1561,
        pageCount = 2465,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "ckb",
        name = "Central Kurdish",
        localName = "کوردی",
        articleCount = 79331,
        pageCount = 252109,
        activeUserCount = 197
    ),
    SupportedLanguage(
        code = "ch",
        name = "Chamorro",
        localName = "Chamoru",
        articleCount = 557,
        pageCount = 2590,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "cbk-zam",
        name = "Chavacano",
        localName = "Chavacano de Zamboanga",
        articleCount = 3235,
        pageCount = 8993,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "ce",
        name = "Chechen",
        localName = "нохчийн",
        articleCount = 693426,
        pageCount = 1327451,
        activeUserCount = 73
    ),
    SupportedLanguage(
        code = "chr",
        name = "Cherokee",
        localName = "ᏣᎳᎩ",
        articleCount = 1002,
        pageCount = 4067,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "chy",
        name = "Cheyenne",
        localName = "Tsetsêhestâhese",
        articleCount = 720,
        pageCount = 2300,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "cu",
        name = "Church Slavic",
        localName = "словѣньскъ / ⰔⰎⰑⰂⰡⰐⰠⰔⰍⰟ",
        articleCount = 1313,
        pageCount = 5889,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "cv",
        name = "Chuvash",
        localName = "чӑвашла",
        articleCount = 58210,
        pageCount = 121479,
        activeUserCount = 60
    ),
    SupportedLanguage(
        code = "ksh",
        name = "Colognian",
        localName = "Ripoarisch",
        articleCount = 3038,
        pageCount = 10802,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "kw",
        name = "Cornish",
        localName = "kernowek",
        articleCount = 7096,
        pageCount = 14779,
        activeUserCount = 33
    ),
    SupportedLanguage(
        code = "co",
        name = "Corsican",
        localName = "corsu",
        articleCount = 8625,
        pageCount = 18023,
        activeUserCount = 54
    ),
    SupportedLanguage(
        code = "crh",
        name = "Crimean Tatar",
        localName = "qırımtatarca",
        articleCount = 29623,
        pageCount = 57602,
        activeUserCount = 43
    ),
    SupportedLanguage(
        code = "hr",
        name = "Croatian",
        localName = "hrvatski",
        articleCount = 228706,
        pageCount = 494472,
        activeUserCount = 1407
    ),
    SupportedLanguage(
        code = "cs",
        name = "Czech",
        localName = "čeština",
        articleCount = 582224,
        pageCount = 1623101,
        activeUserCount = 5590
    ),
    SupportedLanguage(
        code = "dag",
        name = "Dagbani",
        localName = "dagbanli",
        articleCount = 13354,
        pageCount = 24415,
        activeUserCount = 112
    ),
    SupportedLanguage(
        code = "da",
        name = "Danish",
        localName = "dansk",
        articleCount = 311828,
        pageCount = 977819,
        activeUserCount = 1773
    ),
    SupportedLanguage(
        code = "diq",
        name = "Dimli",
        localName = "Zazaki",
        articleCount = 42375,
        pageCount = 63913,
        activeUserCount = 54
    ),
    SupportedLanguage(
        code = "din",
        name = "Dinka",
        localName = "Thuɔŋjäŋ",
        articleCount = 371,
        pageCount = 1130,
        activeUserCount = 8
    ),
    SupportedLanguage(
        code = "dv",
        name = "Divehi",
        localName = "ދިވެހިބަސް",
        articleCount = 3192,
        pageCount = 12509,
        activeUserCount = 20
    ),
    SupportedLanguage(
        code = "dty",
        name = "Doteli",
        localName = "डोटेली",
        articleCount = 3632,
        pageCount = 22233,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "dz",
        name = "Dzongkha",
        localName = "ཇོང་ཁ",
        articleCount = 377,
        pageCount = 5525,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "mhr",
        name = "Eastern Mari",
        localName = "олык марий",
        articleCount = 11325,
        pageCount = 31271,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "eml",
        name = "Emiliano-Romagnolo",
        localName = "emiliàn e rumagnòl",
        articleCount = 13758,
        pageCount = 36878,
        activeUserCount = 45
    ),
    SupportedLanguage(
        code = "myv",
        name = "Erzya",
        localName = "эрзянь",
        articleCount = 7867,
        pageCount = 31525,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "eo",
        name = "Esperanto",
        localName = "Esperanto",
        articleCount = 379272,
        pageCount = 850770,
        activeUserCount = 555
    ),
    SupportedLanguage(
        code = "et",
        name = "Estonian",
        localName = "eesti",
        articleCount = 256481,
        pageCount = 604623,
        activeUserCount = 1431
    ),
    SupportedLanguage(
        code = "ee",
        name = "Ewe",
        localName = "eʋegbe",
        articleCount = 1267,
        pageCount = 4236,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "ext",
        name = "Extremaduran",
        localName = "estremeñu",
        articleCount = 4087,
        pageCount = 9167,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "fat",
        name = "Fanti",
        localName = "mfantse",
        articleCount = 1762,
        pageCount = 4727,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "fo",
        name = "Faroese",
        localName = "føroyskt",
        articleCount = 14180,
        pageCount = 41112,
        activeUserCount = 53
    ),
    SupportedLanguage(
        code = "hif",
        name = "Fiji Hindi",
        localName = "Fiji Hindi",
        articleCount = 12096,
        pageCount = 55316,
        activeUserCount = 72
    ),
    SupportedLanguage(
        code = "fj",
        name = "Fijian",
        localName = "Na Vosa Vakaviti",
        articleCount = 1614,
        pageCount = 4355,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "fi",
        name = "Finnish",
        localName = "suomi",
        articleCount = 609347,
        pageCount = 1564133,
        activeUserCount = 3748
    ),
    SupportedLanguage(
        code = "fon",
        name = "Fon",
        localName = "fɔ̀ngbè",
        articleCount = 3343,
        pageCount = 5071,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "gur",
        name = "Frafra",
        localName = "farefare",
        articleCount = 1331,
        pageCount = 2532,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "fur",
        name = "Friulian",
        localName = "furlan",
        articleCount = 4886,
        pageCount = 11196,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "ff",
        name = "Fula",
        localName = "Fulfulde",
        articleCount = 13303,
        pageCount = 27903,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "gag",
        name = "Gagauz",
        localName = "Gagauz",
        articleCount = 3013,
        pageCount = 7803,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "gl",
        name = "Galician",
        localName = "galego",
        articleCount = 228335,
        pageCount = 561224,
        activeUserCount = 532
    ),
    SupportedLanguage(
        code = "gan",
        name = "Gan",
        localName = "贛語",
        articleCount = 6815,
        pageCount = 34477,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "lg",
        name = "Ganda",
        localName = "Luganda",
        articleCount = 4430,
        pageCount = 8787,
        activeUserCount = 40
    ),
    SupportedLanguage(
        code = "ka",
        name = "Georgian",
        localName = "ქართული",
        articleCount = 188774,
        pageCount = 525295,
        activeUserCount = 529
    ),
    SupportedLanguage(
        code = "gpe",
        name = "Ghanaian Pidgin",
        localName = "Ghanaian Pidgin",
        articleCount = 4148,
        pageCount = 22176,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "glk",
        name = "Gilaki",
        localName = "گیلکی",
        articleCount = 48352,
        pageCount = 57653,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "gom",
        name = "Goan Konkani",
        localName = "गोंयची कोंकणी / Gõychi Konknni",
        articleCount = 3643,
        pageCount = 8907,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "gor",
        name = "Gorontalo",
        localName = "Bahasa Hulontalo",
        articleCount = 14912,
        pageCount = 24433,
        activeUserCount = 44
    ),
    SupportedLanguage(
        code = "got",
        name = "Gothic",
        localName = "𐌲𐌿𐍄𐌹𐍃𐌺",
        articleCount = 1000,
        pageCount = 3982,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "el",
        name = "Greek",
        localName = "Ελληνικά",
        articleCount = 262281,
        pageCount = 737219,
        activeUserCount = 2653
    ),
    SupportedLanguage(
        code = "gn",
        name = "Guarani",
        localName = "Avañe'ẽ",
        articleCount = 5984,
        pageCount = 14082,
        activeUserCount = 34
    ),
    SupportedLanguage(
        code = "gcr",
        name = "Guianan Creole",
        localName = "kriyòl gwiyannen",
        articleCount = 1075,
        pageCount = 2672,
        activeUserCount = 7
    ),
    SupportedLanguage(
        code = "gu",
        name = "Gujarati",
        localName = "ગુજરાતી",
        articleCount = 30752,
        pageCount = 134897,
        activeUserCount = 136
    ),
    SupportedLanguage(
        code = "guw",
        name = "Gun",
        localName = "gungbe",
        articleCount = 1562,
        pageCount = 2782,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "ht",
        name = "Haitian Creole",
        localName = "Kreyòl ayisyen",
        articleCount = 71477,
        pageCount = 92969,
        activeUserCount = 89
    ),
    SupportedLanguage(
        code = "hak",
        name = "Hakka Chinese",
        localName = "客家語 / Hak-kâ-ngî",
        articleCount = 10386,
        pageCount = 19925,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "ha",
        name = "Hausa",
        localName = "Hausa",
        articleCount = 73836,
        pageCount = 112674,
        activeUserCount = 216
    ),
    SupportedLanguage(
        code = "haw",
        name = "Hawaiian",
        localName = "Hawaiʻi",
        articleCount = 2967,
        pageCount = 6210,
        activeUserCount = 62
    ),
    SupportedLanguage(
        code = "he",
        name = "Hebrew",
        localName = "עברית",
        articleCount = 387474,
        pageCount = 1630440,
        activeUserCount = 8618
    ),
    SupportedLanguage(
        code = "hi",
        name = "Hindi",
        localName = "हिन्दी",
        articleCount = 166919,
        pageCount = 1382731,
        activeUserCount = 1194
    ),
    SupportedLanguage(
        code = "hu",
        name = "Hungarian",
        localName = "magyar",
        articleCount = 563982,
        pageCount = 1603288,
        activeUserCount = 3577
    ),
    SupportedLanguage(
        code = "iba",
        name = "Iban",
        localName = "Jaku Iban",
        articleCount = 1871,
        pageCount = 5033,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "is",
        name = "Icelandic",
        localName = "íslenska",
        articleCount = 60906,
        pageCount = 158981,
        activeUserCount = 267
    ),
    SupportedLanguage(
        code = "io",
        name = "Ido",
        localName = "Ido",
        articleCount = 60329,
        pageCount = 89469,
        activeUserCount = 71
    ),
    SupportedLanguage(
        code = "igl",
        name = "Igala",
        localName = "Igala",
        articleCount = 943,
        pageCount = 1350,
        activeUserCount = 9
    ),
    SupportedLanguage(
        code = "ig",
        name = "Igbo",
        localName = "Igbo",
        articleCount = 44175,
        pageCount = 60641,
        activeUserCount = 96
    ),
    SupportedLanguage(
        code = "ilo",
        name = "Iloko",
        localName = "Ilokano",
        articleCount = 15441,
        pageCount = 70547,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "smn",
        name = "Inari Sami",
        localName = "anarâškielâ",
        articleCount = 6508,
        pageCount = 28994,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "id",
        name = "Indonesian",
        localName = "Bahasa Indonesia",
        articleCount = 758107,
        pageCount = 4188520,
        activeUserCount = 6295
    ),
    SupportedLanguage(
        code = "inh",
        name = "Ingush",
        localName = "гӀалгӀай",
        articleCount = 2412,
        pageCount = 16172,
        activeUserCount = 27
    ),
    SupportedLanguage(
        code = "ia",
        name = "Interlingua",
        localName = "interlingua",
        articleCount = 30160,
        pageCount = 45955,
        activeUserCount = 45
    ),
    SupportedLanguage(
        code = "ie",
        name = "Interlingue",
        localName = "Interlingue",
        articleCount = 13403,
        pageCount = 17696,
        activeUserCount = 33
    ),
    SupportedLanguage(
        code = "iu",
        name = "Inuktitut",
        localName = "ᐃᓄᒃᑎᑐᑦ / inuktitut",
        articleCount = 425,
        pageCount = 3012,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "ik",
        name = "Inupiaq",
        localName = "Iñupiatun",
        articleCount = 664,
        pageCount = 2875,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "ga",
        name = "Irish",
        localName = "Gaeilge",
        articleCount = 62720,
        pageCount = 112384,
        activeUserCount = 125
    ),
    SupportedLanguage(
        code = "jam",
        name = "Jamaican Creole English",
        localName = "Patois",
        articleCount = 1729,
        pageCount = 3132,
        activeUserCount = 11
    ),
    SupportedLanguage(
        code = "jv",
        name = "Javanese",
        localName = "Jawa",
        articleCount = 74798,
        pageCount = 185632,
        activeUserCount = 221
    ),
    SupportedLanguage(
        code = "kbd",
        name = "Kabardian",
        localName = "адыгэбзэ",
        articleCount = 1640,
        pageCount = 7107,
        activeUserCount = 20
    ),
    SupportedLanguage(
        code = "kbp",
        name = "Kabiye",
        localName = "Kabɩyɛ",
        articleCount = 1715,
        pageCount = 3451,
        activeUserCount = 5
    ),
    SupportedLanguage(
        code = "kab",
        name = "Kabyle",
        localName = "Taqbaylit",
        articleCount = 7010,
        pageCount = 17990,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "xal",
        name = "Kalmyk",
        localName = "хальмг",
        articleCount = 1597,
        pageCount = 12388,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "kn",
        name = "Kannada",
        localName = "ಕನ್ನಡ",
        articleCount = 34153,
        pageCount = 157900,
        activeUserCount = 281
    ),
    SupportedLanguage(
        code = "kaa",
        name = "Kara-Kalpak",
        localName = "Qaraqalpaqsha",
        articleCount = 10921,
        pageCount = 29167,
        activeUserCount = 41
    ),
    SupportedLanguage(
        code = "krc",
        name = "Karachay-Balkar",
        localName = "къарачай-малкъар",
        articleCount = 2683,
        pageCount = 16896,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "ks",
        name = "Kashmiri",
        localName = "کٲشُر",
        articleCount = 8445,
        pageCount = 20751,
        activeUserCount = 33
    ),
    SupportedLanguage(
        code = "csb",
        name = "Kashubian",
        localName = "kaszëbsczi",
        articleCount = 5498,
        pageCount = 8905,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "kk",
        name = "Kazakh",
        localName = "қазақша",
        articleCount = 241505,
        pageCount = 657989,
        activeUserCount = 408
    ),
    SupportedLanguage(
        code = "km",
        name = "Khmer",
        localName = "ភាសាខ្មែរ",
        articleCount = 11861,
        pageCount = 36856,
        activeUserCount = 179
    ),
    SupportedLanguage(
        code = "ki",
        name = "Kikuyu",
        localName = "Gĩkũyũ",
        articleCount = 1938,
        pageCount = 3732,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "rw",
        name = "Kinyarwanda",
        localName = "Ikinyarwanda",
        articleCount = 8917,
        pageCount = 17719,
        activeUserCount = 69
    ),
    SupportedLanguage(
        code = "kge",
        name = "Komering",
        localName = "Kumoring",
        articleCount = 2802,
        pageCount = 4539,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "kv",
        name = "Komi",
        localName = "коми",
        articleCount = 5731,
        pageCount = 19825,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "koi",
        name = "Komi-Permyak",
        localName = "перем коми",
        articleCount = 3468,
        pageCount = 13467,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "kg",
        name = "Kongo",
        localName = "Kongo",
        articleCount = 1570,
        pageCount = 4028,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "ko",
        name = "Korean",
        localName = "한국어",
        articleCount = 732425,
        pageCount = 3488535,
        activeUserCount = 7535
    ),
    SupportedLanguage(
        code = "avk",
        name = "Kotava",
        localName = "Kotava",
        articleCount = 29900,
        pageCount = 36352,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "ku",
        name = "Kurdish",
        localName = "kurdî",
        articleCount = 90938,
        pageCount = 289900,
        activeUserCount = 151
    ),
    SupportedLanguage(
        code = "kus",
        name = "Kusaal",
        localName = "Kʋsaal",
        articleCount = 1247,
        pageCount = 1839,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "ky",
        name = "Kyrgyz",
        localName = "кыргызча",
        articleCount = 76126,
        pageCount = 110457,
        activeUserCount = 140
    ),
    SupportedLanguage(
        code = "lld",
        name = "Ladin",
        localName = "Ladin",
        articleCount = 180829,
        pageCount = 188193,
        activeUserCount = 65
    ),
    SupportedLanguage(
        code = "lad",
        name = "Ladino",
        localName = "Ladino",
        articleCount = 3896,
        pageCount = 13642,
        activeUserCount = 33
    ),
    SupportedLanguage(
        code = "lbe",
        name = "Lak",
        localName = "лакку",
        articleCount = 1194,
        pageCount = 16184,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "lo",
        name = "Lao",
        localName = "ລາວ",
        articleCount = 5245,
        pageCount = 15639,
        activeUserCount = 65
    ),
    SupportedLanguage(
        code = "ltg",
        name = "Latgalian",
        localName = "latgaļu",
        articleCount = 1112,
        pageCount = 3262,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "la",
        name = "Latin",
        localName = "Latina",
        articleCount = 140819,
        pageCount = 291189,
        activeUserCount = 216
    ),
    SupportedLanguage(
        code = "lv",
        name = "Latvian",
        localName = "latviešu",
        articleCount = 139356,
        pageCount = 558005,
        activeUserCount = 713
    ),
    SupportedLanguage(
        code = "lez",
        name = "Lezghian",
        localName = "лезги",
        articleCount = 4454,
        pageCount = 14941,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "lij",
        name = "Ligurian",
        localName = "Ligure",
        articleCount = 11457,
        pageCount = 28127,
        activeUserCount = 44
    ),
    SupportedLanguage(
        code = "li",
        name = "Limburgish",
        localName = "Limburgs",
        articleCount = 15146,
        pageCount = 68668,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "ln",
        name = "Lingala",
        localName = "lingála",
        articleCount = 4846,
        pageCount = 11477,
        activeUserCount = 25
    ),
    SupportedLanguage(
        code = "lfn",
        name = "Lingua Franca Nova",
        localName = "Lingua Franca Nova",
        articleCount = 4501,
        pageCount = 7206,
        activeUserCount = 33
    ),
    SupportedLanguage(
        code = "zh-classical",
        name = "Literary Chinese",
        localName = "文言",
        articleCount = 13909,
        pageCount = 117650,
        activeUserCount = 83
    ),
    SupportedLanguage(
        code = "lt",
        name = "Lithuanian",
        localName = "lietuvių",
        articleCount = 224443,
        pageCount = 560354,
        activeUserCount = 890
    ),
    SupportedLanguage(
        code = "olo",
        name = "Livvi-Karelian",
        localName = "livvinkarjala",
        articleCount = 4651,
        pageCount = 14034,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "jbo",
        name = "Lojban",
        localName = "la .lojban.",
        articleCount = 1351,
        pageCount = 5820,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "lmo",
        name = "Lombard",
        localName = "lombard",
        articleCount = 79918,
        pageCount = 152757,
        activeUserCount = 98
    ),
    SupportedLanguage(
        code = "nds",
        name = "Low German",
        localName = "Plattdüütsch",
        articleCount = 85775,
        pageCount = 185268,
        activeUserCount = 62
    ),
    SupportedLanguage(
        code = "nds-nl",
        name = "Low Saxon",
        localName = "Nedersaksies",
        articleCount = 8067,
        pageCount = 21958,
        activeUserCount = 46
    ),
    SupportedLanguage(
        code = "dsb",
        name = "Lower Sorbian",
        localName = "dolnoserbski",
        articleCount = 3428,
        pageCount = 11639,
        activeUserCount = 29
    ),
    SupportedLanguage(
        code = "lb",
        name = "Luxembourgish",
        localName = "Lëtzebuergesch",
        articleCount = 66279,
        pageCount = 144235,
        activeUserCount = 142
    ),
    SupportedLanguage(
        code = "mk",
        name = "Macedonian",
        localName = "македонски",
        articleCount = 157325,
        pageCount = 590953,
        activeUserCount = 396
    ),
    SupportedLanguage(
        code = "mad",
        name = "Madurese",
        localName = "Madhurâ",
        articleCount = 2022,
        pageCount = 11409,
        activeUserCount = 68
    ),
    SupportedLanguage(
        code = "mai",
        name = "Maithili",
        localName = "मैथिली",
        articleCount = 14252,
        pageCount = 45343,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "mg",
        name = "Malagasy",
        localName = "Malagasy",
        articleCount = 101612,
        pageCount = 259329,
        activeUserCount = 70
    ),
    SupportedLanguage(
        code = "ms",
        name = "Malay",
        localName = "Bahasa Melayu",
        articleCount = 435165,
        pageCount = 1169696,
        activeUserCount = 2655
    ),
    SupportedLanguage(
        code = "ml",
        name = "Malayalam",
        localName = "മലയാളം",
        articleCount = 87443,
        pageCount = 548811,
        activeUserCount = 520
    ),
    SupportedLanguage(
        code = "mt",
        name = "Maltese",
        localName = "Malti",
        articleCount = 7671,
        pageCount = 23540,
        activeUserCount = 53
    ),
    SupportedLanguage(
        code = "mni",
        name = "Manipuri",
        localName = "ꯃꯤꯇꯩ ꯂꯣꯟ",
        articleCount = 10456,
        pageCount = 17379,
        activeUserCount = 32
    ),
    SupportedLanguage(
        code = "gv",
        name = "Manx",
        localName = "Gaelg",
        articleCount = 7051,
        pageCount = 39687,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "mr",
        name = "Marathi",
        localName = "मराठी",
        articleCount = 100876,
        pageCount = 326448,
        activeUserCount = 415
    ),
    SupportedLanguage(
        code = "mzn",
        name = "Mazanderani",
        localName = "مازِرونی",
        articleCount = 64487,
        pageCount = 107251,
        activeUserCount = 47
    ),
    SupportedLanguage(
        code = "min",
        name = "Minangkabau",
        localName = "Minangkabau",
        articleCount = 228708,
        pageCount = 473772,
        activeUserCount = 71
    ),
    SupportedLanguage(
        code = "cdo",
        name = "Mindong",
        localName = "閩東語 / Mìng-dĕ̤ng-ngṳ̄",
        articleCount = 16702,
        pageCount = 33577,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "xmf",
        name = "Mingrelian",
        localName = "მარგალური",
        articleCount = 21959,
        pageCount = 41598,
        activeUserCount = 38
    ),
    SupportedLanguage(
        code = "zh-min-nan",
        name = "Minnan",
        localName = "閩南語 / Bân-lâm-gí",
        articleCount = 433870,
        pageCount = 1075214,
        activeUserCount = 117
    ),
    SupportedLanguage(
        code = "mwl",
        name = "Mirandese",
        localName = "Mirandés",
        articleCount = 4283,
        pageCount = 10735,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "mdf",
        name = "Moksha",
        localName = "мокшень",
        articleCount = 7616,
        pageCount = 23750,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "mnw",
        name = "Mon",
        localName = "ဘာသာမန်",
        articleCount = 1966,
        pageCount = 6799,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "mn",
        name = "Mongolian",
        localName = "монгол",
        articleCount = 26630,
        pageCount = 113661,
        activeUserCount = 299
    ),
    SupportedLanguage(
        code = "ary",
        name = "Moroccan Arabic",
        localName = "الدارجة",
        articleCount = 10942,
        pageCount = 91109,
        activeUserCount = 63
    ),
    SupportedLanguage(
        code = "mos",
        name = "Mossi",
        localName = "moore",
        articleCount = 1307,
        pageCount = 2092,
        activeUserCount = 10
    ),
    SupportedLanguage(
        code = "mi",
        name = "Māori",
        localName = "Māori",
        articleCount = 8018,
        pageCount = 15423,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "nah",
        name = "Nahuatl",
        localName = "Nāhuatl",
        articleCount = 4281,
        pageCount = 13348,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "nv",
        name = "Navajo",
        localName = "Diné bizaad",
        articleCount = 22664,
        pageCount = 37320,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "nap",
        name = "Neapolitan",
        localName = "Napulitano",
        articleCount = 14937,
        pageCount = 24171,
        activeUserCount = 31
    ),
    SupportedLanguage(
        code = "ne",
        name = "Nepali",
        localName = "नेपाली",
        articleCount = 29354,
        pageCount = 112176,
        activeUserCount = 153
    ),
    SupportedLanguage(
        code = "new",
        name = "Newari",
        localName = "नेपाल भाषा",
        articleCount = 72917,
        pageCount = 179276,
        activeUserCount = 28
    ),
    SupportedLanguage(
        code = "nia",
        name = "Nias",
        localName = "Li Niha",
        articleCount = 1767,
        pageCount = 4466,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "pcm",
        name = "Nigerian Pidgin",
        localName = "Naijá",
        articleCount = 1531,
        pageCount = 2790,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "nrm",
        name = "Norman",
        localName = "Nouormand",
        articleCount = 5056,
        pageCount = 10685,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "frr",
        name = "Northern Frisian",
        localName = "Nordfriisk",
        articleCount = 20688,
        pageCount = 51309,
        activeUserCount = 36
    ),
    SupportedLanguage(
        code = "se",
        name = "Northern Sami",
        localName = "davvisámegiella",
        articleCount = 7905,
        pageCount = 21131,
        activeUserCount = 29
    ),
    SupportedLanguage(
        code = "nso",
        name = "Northern Sotho",
        localName = "Sesotho sa Leboa",
        articleCount = 8783,
        pageCount = 11493,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "no",
        name = "Norwegian",
        localName = "norsk",
        articleCount = 662708,
        pageCount = 1881799,
        activeUserCount = 2745
    ),
    SupportedLanguage(
        code = "nn",
        name = "Norwegian Nynorsk",
        localName = "norsk nynorsk",
        articleCount = 177014,
        pageCount = 398776,
        activeUserCount = 206
    ),
    SupportedLanguage(
        code = "nov",
        name = "Novial",
        localName = "Novial",
        articleCount = 1892,
        pageCount = 4834,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "nup",
        name = "Nupe",
        localName = "Nupe",
        articleCount = 538,
        pageCount = 1057,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "ny",
        name = "Nyanja",
        localName = "Chi-Chewa",
        articleCount = 1099,
        pageCount = 5415,
        activeUserCount = 17
    ),
    SupportedLanguage(
        code = "nqo",
        name = "N’Ko",
        localName = "ߒߞߏ",
        articleCount = 1584,
        pageCount = 3420,
        activeUserCount = 11
    ),
    SupportedLanguage(
        code = "ann",
        name = "Obolo",
        localName = "Obolo",
        articleCount = 433,
        pageCount = 896,
        activeUserCount = 11
    ),
    SupportedLanguage(
        code = "oc",
        name = "Occitan",
        localName = "occitan",
        articleCount = 90432,
        pageCount = 166481,
        activeUserCount = 186
    ),
    SupportedLanguage(
        code = "or",
        name = "Odia",
        localName = "ଓଡ଼ିଆ",
        articleCount = 20144,
        pageCount = 85624,
        activeUserCount = 92
    ),
    SupportedLanguage(
        code = "ang",
        name = "Old English",
        localName = "Ænglisc",
        articleCount = 5044,
        pageCount = 21330,
        activeUserCount = 59
    ),
    SupportedLanguage(
        code = "om",
        name = "Oromo",
        localName = "Oromoo",
        articleCount = 1960,
        pageCount = 5345,
        activeUserCount = 29
    ),
    SupportedLanguage(
        code = "os",
        name = "Ossetic",
        localName = "ирон",
        articleCount = 21431,
        pageCount = 76649,
        activeUserCount = 36
    ),
    SupportedLanguage(
        code = "blk",
        name = "Pa'O",
        localName = "ပအိုဝ်ႏဘာႏသာႏ",
        articleCount = 2909,
        pageCount = 8737,
        activeUserCount = 8
    ),
    SupportedLanguage(
        code = "pwn",
        name = "Paiwan",
        localName = "pinayuanan",
        articleCount = 376,
        pageCount = 635,
        activeUserCount = 5
    ),
    SupportedLanguage(
        code = "pfl",
        name = "Palatine German",
        localName = "Pälzisch",
        articleCount = 2833,
        pageCount = 7075,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "pi",
        name = "Pali",
        localName = "पालि",
        articleCount = 291,
        pageCount = 1830,
        activeUserCount = 10
    ),
    SupportedLanguage(
        code = "pam",
        name = "Pampanga",
        localName = "Kapampangan",
        articleCount = 10149,
        pageCount = 23266,
        activeUserCount = 24
    ),
    SupportedLanguage(
        code = "pag",
        name = "Pangasinan",
        localName = "Pangasinan",
        articleCount = 2620,
        pageCount = 6745,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "rsk",
        name = "Pannonian Rusyn",
        localName = "руски",
        articleCount = 1014,
        pageCount = 2254,
        activeUserCount = 20
    ),
    SupportedLanguage(
        code = "pap",
        name = "Papiamento",
        localName = "Papiamentu",
        articleCount = 5045,
        pageCount = 10457,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "ps",
        name = "Pashto",
        localName = "پښتو",
        articleCount = 20836,
        pageCount = 75125,
        activeUserCount = 69
    ),
    SupportedLanguage(
        code = "pdc",
        name = "Pennsylvania German",
        localName = "Deitsch",
        articleCount = 2046,
        pageCount = 6054,
        activeUserCount = 32
    ),
    SupportedLanguage(
        code = "fa",
        name = "Persian",
        localName = "فارسی",
        articleCount = 1064645,
        pageCount = 6004245,
        activeUserCount = 11227
    ),
    SupportedLanguage(
        code = "pcd",
        name = "Picard",
        localName = "Picard",
        articleCount = 6045,
        pageCount = 12037,
        activeUserCount = 37
    ),
    SupportedLanguage(
        code = "pms",
        name = "Piedmontese",
        localName = "Piemontèis",
        articleCount = 70747,
        pageCount = 106794,
        activeUserCount = 38
    ),
    SupportedLanguage(
        code = "pnt",
        name = "Pontic",
        localName = "Ποντιακά",
        articleCount = 520,
        pageCount = 2498,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "pt",
        name = "Portuguese",
        localName = "português",
        articleCount = 1161907,
        pageCount = 6019944,
        activeUserCount = 8185
    ),
    SupportedLanguage(
        code = "pa",
        name = "Punjabi",
        localName = "ਪੰਜਾਬੀ",
        articleCount = 59296,
        pageCount = 190780,
        activeUserCount = 172
    ),
    SupportedLanguage(
        code = "qu",
        name = "Quechua",
        localName = "Runa Simi",
        articleCount = 24365,
        pageCount = 58502,
        activeUserCount = 55
    ),
    SupportedLanguage(
        code = "ro",
        name = "Romanian",
        localName = "română",
        articleCount = 518839,
        pageCount = 2939305,
        activeUserCount = 2267
    ),
    SupportedLanguage(
        code = "rm",
        name = "Romansh",
        localName = "rumantsch",
        articleCount = 3813,
        pageCount = 9786,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "rn",
        name = "Rundi",
        localName = "ikirundi",
        articleCount = 703,
        pageCount = 2729,
        activeUserCount = 9
    ),
    SupportedLanguage(
        code = "bxr",
        name = "Russia Buriat",
        localName = "буряад",
        articleCount = 2911,
        pageCount = 11326,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "rue",
        name = "Rusyn",
        localName = "русиньскый",
        articleCount = 10159,
        pageCount = 22372,
        activeUserCount = 40
    ),
    SupportedLanguage(
        code = "szy",
        name = "Sakizaya",
        localName = "Sakizaya",
        articleCount = 2735,
        pageCount = 6326,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "sm",
        name = "Samoan",
        localName = "Gagana Samoa",
        articleCount = 1201,
        pageCount = 6171,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "bat-smg",
        name = "Samogitian",
        localName = "žemaitėška",
        articleCount = 17274,
        pageCount = 30070,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "sg",
        name = "Sango",
        localName = "Sängö",
        articleCount = 447,
        pageCount = 2070,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "sa",
        name = "Sanskrit",
        localName = "संस्कृतम्",
        articleCount = 12418,
        pageCount = 81493,
        activeUserCount = 140
    ),
    SupportedLanguage(
        code = "sat",
        name = "Santali",
        localName = "ᱥᱟᱱᱛᱟᱲᱤ",
        articleCount = 14151,
        pageCount = 30208,
        activeUserCount = 87
    ),
    SupportedLanguage(
        code = "skr",
        name = "Saraiki",
        localName = "سرائیکی",
        articleCount = 24336,
        pageCount = 28776,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "sc",
        name = "Sardinian",
        localName = "sardu",
        articleCount = 7737,
        pageCount = 17547,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "stq",
        name = "Saterland Frisian",
        localName = "Seeltersk",
        articleCount = 4130,
        pageCount = 10822,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "sco",
        name = "Scots",
        localName = "Scots",
        articleCount = 34281,
        pageCount = 138204,
        activeUserCount = 119
    ),
    SupportedLanguage(
        code = "gd",
        name = "Scottish Gaelic",
        localName = "Gàidhlig",
        articleCount = 16018,
        pageCount = 32592,
        activeUserCount = 37
    ),
    SupportedLanguage(
        code = "sr",
        name = "Serbian",
        localName = "српски / srpski",
        articleCount = 713852,
        pageCount = 4220570,
        activeUserCount = 2244
    ),
    SupportedLanguage(
        code = "sh",
        name = "Serbo-Croatian",
        localName = "srpskohrvatski / српскохрватски",
        articleCount = 461240,
        pageCount = 4627339,
        activeUserCount = 426
    ),
    SupportedLanguage(
        code = "shn",
        name = "Shan",
        localName = "တႆး",
        articleCount = 14389,
        pageCount = 34579,
        activeUserCount = 117
    ),
    SupportedLanguage(
        code = "sn",
        name = "Shona",
        localName = "chiShona",
        articleCount = 11498,
        pageCount = 20500,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "scn",
        name = "Sicilian",
        localName = "sicilianu",
        articleCount = 26271,
        pageCount = 56040,
        activeUserCount = 71
    ),
    SupportedLanguage(
        code = "szl",
        name = "Silesian",
        localName = "ślůnski",
        articleCount = 59647,
        pageCount = 76048,
        activeUserCount = 51
    ),
    SupportedLanguage(
        code = "simple",
        name = "Simple English",
        localName = "Simple English",
        articleCount = 277524,
        pageCount = 920799,
        activeUserCount = 4733
    ),
    SupportedLanguage(
        code = "sd",
        name = "Sindhi",
        localName = "سنڌي",
        articleCount = 19721,
        pageCount = 71030,
        activeUserCount = 39
    ),
    SupportedLanguage(
        code = "si",
        name = "Sinhala",
        localName = "සිංහල",
        articleCount = 24937,
        pageCount = 166479,
        activeUserCount = 183
    ),
    SupportedLanguage(
        code = "sk",
        name = "Slovak",
        localName = "slovenčina",
        articleCount = 257152,
        pageCount = 596527,
        activeUserCount = 1507
    ),
    SupportedLanguage(
        code = "sl",
        name = "Slovenian",
        localName = "slovenščina",
        articleCount = 195926,
        pageCount = 500029,
        activeUserCount = 771
    ),
    SupportedLanguage(
        code = "so",
        name = "Somali",
        localName = "Soomaaliga",
        articleCount = 10006,
        pageCount = 30077,
        activeUserCount = 191
    ),
    SupportedLanguage(
        code = "azb",
        name = "South Azerbaijani",
        localName = "تۆرکجه",
        articleCount = 244456,
        pageCount = 579941,
        activeUserCount = 120
    ),
    SupportedLanguage(
        code = "nr",
        name = "South Ndebele",
        localName = "isiNdebele seSewula",
        articleCount = 279,
        pageCount = 864,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "alt",
        name = "Southern Altai",
        localName = "алтай тил",
        articleCount = 1103,
        pageCount = 7028,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "dga",
        name = "Southern Dagaare",
        localName = "Dagaare",
        articleCount = 3005,
        pageCount = 6534,
        activeUserCount = 22
    ),
    SupportedLanguage(
        code = "st",
        name = "Southern Sotho",
        localName = "Sesotho",
        articleCount = 1570,
        pageCount = 5459,
        activeUserCount = 47
    ),
    SupportedLanguage(
        code = "srn",
        name = "Sranan Tongo",
        localName = "Sranantongo",
        articleCount = 1128,
        pageCount = 2731,
        activeUserCount = 11
    ),
    SupportedLanguage(
        code = "zgh",
        name = "Standard Moroccan Tamazight",
        localName = "ⵜⴰⵎⴰⵣⵉⵖⵜ ⵜⴰⵏⴰⵡⴰⵢⵜ",
        articleCount = 12021,
        pageCount = 41242,
        activeUserCount = 80
    ),
    SupportedLanguage(
        code = "su",
        name = "Sundanese",
        localName = "Sunda",
        articleCount = 62166,
        pageCount = 99775,
        activeUserCount = 59
    ),
    SupportedLanguage(
        code = "sw",
        name = "Swahili",
        localName = "Kiswahili",
        articleCount = 103148,
        pageCount = 207662,
        activeUserCount = 415
    ),
    SupportedLanguage(
        code = "ss",
        name = "Swati",
        localName = "SiSwati",
        articleCount = 1132,
        pageCount = 3485,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "syl",
        name = "Sylheti",
        localName = "ꠍꠤꠟꠐꠤ",
        articleCount = 1203,
        pageCount = 6252,
        activeUserCount = 8
    ),
    SupportedLanguage(
        code = "shi",
        name = "Tachelhit",
        localName = "Taclḥit",
        articleCount = 10882,
        pageCount = 14943,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "tl",
        name = "Tagalog",
        localName = "Tagalog",
        articleCount = 48801,
        pageCount = 248016,
        activeUserCount = 316
    ),
    SupportedLanguage(
        code = "ty",
        name = "Tahitian",
        localName = "reo tahiti",
        articleCount = 1249,
        pageCount = 3079,
        activeUserCount = 11
    ),
    SupportedLanguage(
        code = "tdd",
        name = "Tai Nuea",
        localName = "ᥖᥭᥰ ᥖᥬᥲ ᥑᥨᥒᥰ",
        articleCount = 448,
        pageCount = 2194,
        activeUserCount = 15
    ),
    SupportedLanguage(
        code = "tg",
        name = "Tajik",
        localName = "тоҷикӣ",
        articleCount = 116162,
        pageCount = 283933,
        activeUserCount = 133
    ),
    SupportedLanguage(
        code = "tly",
        name = "Talysh",
        localName = "tolışi",
        articleCount = 10060,
        pageCount = 13871,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "ta",
        name = "Tamil",
        localName = "தமிழ்",
        articleCount = 179188,
        pageCount = 610461,
        activeUserCount = 619
    ),
    SupportedLanguage(
        code = "roa-tara",
        name = "Tarantino",
        localName = "tarandíne",
        articleCount = 9497,
        pageCount = 18908,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "trv",
        name = "Taroko",
        localName = "Seediq",
        articleCount = 1201,
        pageCount = 2212,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "tt",
        name = "Tatar",
        localName = "татарча / tatarça",
        articleCount = 580178,
        pageCount = 929604,
        activeUserCount = 101
    ),
    SupportedLanguage(
        code = "te",
        name = "Telugu",
        localName = "తెలుగు",
        articleCount = 117528,
        pageCount = 401487,
        activeUserCount = 395
    ),
    SupportedLanguage(
        code = "tet",
        name = "Tetum",
        localName = "tetun",
        articleCount = 1380,
        pageCount = 3954,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "th",
        name = "Thai",
        localName = "ไทย",
        articleCount = 178187,
        pageCount = 1149155,
        activeUserCount = 3518
    ),
    SupportedLanguage(
        code = "bo",
        name = "Tibetan",
        localName = "བོད་ཡིག",
        articleCount = 7570,
        pageCount = 21130,
        activeUserCount = 48
    ),
    SupportedLanguage(
        code = "tig",
        name = "Tigre",
        localName = "ትግሬ",
        articleCount = 41,
        pageCount = 476,
        activeUserCount = 12
    ),
    SupportedLanguage(
        code = "ti",
        name = "Tigrinya",
        localName = "ትግርኛ",
        articleCount = 335,
        pageCount = 3031,
        activeUserCount = 9
    ),
    SupportedLanguage(
        code = "tpi",
        name = "Tok Pisin",
        localName = "Tok Pisin",
        articleCount = 1407,
        pageCount = 5797,
        activeUserCount = 18
    ),
    SupportedLanguage(
        code = "tok",
        name = "Toki Pona",
        localName = "toki pona",
        articleCount = 3346,
        pageCount = 8701,
        activeUserCount = 206
    ),
    SupportedLanguage(
        code = "to",
        name = "Tongan",
        localName = "lea faka-Tonga",
        articleCount = 2043,
        pageCount = 5536,
        activeUserCount = 6
    ),
    SupportedLanguage(
        code = "ts",
        name = "Tsonga",
        localName = "Xitsonga",
        articleCount = 950,
        pageCount = 4238,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "tn",
        name = "Tswana",
        localName = "Setswana",
        articleCount = 3769,
        pageCount = 8177,
        activeUserCount = 50
    ),
    SupportedLanguage(
        code = "tcy",
        name = "Tulu",
        localName = "ತುಳು",
        articleCount = 2949,
        pageCount = 18100,
        activeUserCount = 35
    ),
    SupportedLanguage(
        code = "tum",
        name = "Tumbuka",
        localName = "chiTumbuka",
        articleCount = 18795,
        pageCount = 39194,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "tr",
        name = "Turkish",
        localName = "Türkçe",
        articleCount = 655610,
        pageCount = 3389834,
        activeUserCount = 5492
    ),
    SupportedLanguage(
        code = "tk",
        name = "Turkmen",
        localName = "Türkmençe",
        articleCount = 7055,
        pageCount = 17548,
        activeUserCount = 102
    ),
    SupportedLanguage(
        code = "tyv",
        name = "Tuvinian",
        localName = "тыва дыл",
        articleCount = 4064,
        pageCount = 14571,
        activeUserCount = 13
    ),
    SupportedLanguage(
        code = "tw",
        name = "Twi",
        localName = "Twi",
        articleCount = 4619,
        pageCount = 8593,
        activeUserCount = 21
    ),
    SupportedLanguage(
        code = "kcg",
        name = "Tyap",
        localName = "Tyap",
        articleCount = 1469,
        pageCount = 6427,
        activeUserCount = 10
    ),
    SupportedLanguage(
        code = "udm",
        name = "Udmurt",
        localName = "удмурт",
        articleCount = 5713,
        pageCount = 20072,
        activeUserCount = 30
    ),
    SupportedLanguage(
        code = "hsb",
        name = "Upper Sorbian",
        localName = "hornjoserbsce",
        articleCount = 14217,
        pageCount = 36389,
        activeUserCount = 42
    ),
    SupportedLanguage(
        code = "ur",
        name = "Urdu",
        localName = "اردو",
        articleCount = 236397,
        pageCount = 2306240,
        activeUserCount = 398
    ),
    SupportedLanguage(
        code = "ug",
        name = "Uyghur",
        localName = "ئۇيغۇرچە / Uyghurche",
        articleCount = 9616,
        pageCount = 17010,
        activeUserCount = 36
    ),
    SupportedLanguage(
        code = "uz",
        name = "Uzbek",
        localName = "oʻzbekcha / ўзбекча",
        articleCount = 320585,
        pageCount = 1175823,
        activeUserCount = 968
    ),
    SupportedLanguage(
        code = "ve",
        name = "Venda",
        localName = "Tshivenda",
        articleCount = 822,
        pageCount = 2419,
        activeUserCount = 9
    ),
    SupportedLanguage(
        code = "vec",
        name = "Venetian",
        localName = "vèneto",
        articleCount = 69530,
        pageCount = 143320,
        activeUserCount = 58
    ),
    SupportedLanguage(
        code = "vep",
        name = "Veps",
        localName = "vepsän kel’",
        articleCount = 7078,
        pageCount = 38185,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "rmy",
        name = "Vlax Romani",
        localName = "romani čhib",
        articleCount = 756,
        pageCount = 2842,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "vo",
        name = "Volapük",
        localName = "Volapük",
        articleCount = 46702,
        pageCount = 164461,
        activeUserCount = 27
    ),
    SupportedLanguage(
        code = "fiu-vro",
        name = "Võro",
        localName = "võro",
        articleCount = 6880,
        pageCount = 13042,
        activeUserCount = 45
    ),
    SupportedLanguage(
        code = "wa",
        name = "Walloon",
        localName = "walon",
        articleCount = 12823,
        pageCount = 30727,
        activeUserCount = 34
    ),
    SupportedLanguage(
        code = "war",
        name = "Waray",
        localName = "Winaray",
        articleCount = 1266838,
        pageCount = 2870684,
        activeUserCount = 103
    ),
    SupportedLanguage(
        code = "guc",
        name = "Wayuu",
        localName = "wayuunaiki",
        articleCount = 689,
        pageCount = 1359,
        activeUserCount = 42
    ),
    SupportedLanguage(
        code = "cy",
        name = "Welsh",
        localName = "Cymraeg",
        articleCount = 283860,
        pageCount = 536084,
        activeUserCount = 175
    ),
    SupportedLanguage(
        code = "bdr",
        name = "West Coast Bajau",
        localName = "Bajau Sama",
        articleCount = 236,
        pageCount = 1322,
        activeUserCount = 16
    ),
    SupportedLanguage(
        code = "vls",
        name = "West Flemish",
        localName = "West-Vlams",
        articleCount = 8236,
        pageCount = 22477,
        activeUserCount = 48
    ),
    SupportedLanguage(
        code = "hyw",
        name = "Western Armenian",
        localName = "Արեւմտահայերէն",
        articleCount = 13313,
        pageCount = 28865,
        activeUserCount = 48
    ),
    SupportedLanguage(
        code = "fy",
        name = "Western Frisian",
        localName = "Frysk",
        articleCount = 58825,
        pageCount = 176329,
        activeUserCount = 128
    ),
    SupportedLanguage(
        code = "mrj",
        name = "Western Mari",
        localName = "кырык мары",
        articleCount = 10429,
        pageCount = 21195,
        activeUserCount = 19
    ),
    SupportedLanguage(
        code = "pnb",
        name = "Western Punjabi",
        localName = "پنجابی",
        articleCount = 74811,
        pageCount = 140711,
        activeUserCount = 56
    ),
    SupportedLanguage(
        code = "wo",
        name = "Wolof",
        localName = "Wolof",
        articleCount = 1745,
        pageCount = 5567,
        activeUserCount = 26
    ),
    SupportedLanguage(
        code = "wuu",
        name = "Wu",
        localName = "吴语",
        articleCount = 47074,
        pageCount = 71912,
        activeUserCount = 123
    ),
    SupportedLanguage(
        code = "xh",
        name = "Xhosa",
        localName = "isiXhosa",
        articleCount = 2319,
        pageCount = 5574,
        activeUserCount = 23
    ),
    SupportedLanguage(
        code = "sah",
        name = "Yakut",
        localName = "саха тыла",
        articleCount = 17841,
        pageCount = 53992,
        activeUserCount = 61
    ),
    SupportedLanguage(
        code = "yi",
        name = "Yiddish",
        localName = "ייִדיש",
        articleCount = 15641,
        pageCount = 44424,
        activeUserCount = 75
    ),
    SupportedLanguage(
        code = "yo",
        name = "Yoruba",
        localName = "Yorùbá",
        articleCount = 36385,
        pageCount = 61167,
        activeUserCount = 100
    ),
    SupportedLanguage(
        code = "zea",
        name = "Zeelandic",
        localName = "Zeêuws",
        articleCount = 7099,
        pageCount = 13518,
        activeUserCount = 20
    ),
    SupportedLanguage(
        code = "za",
        name = "Zhuang",
        localName = "Vahcuengh",
        articleCount = 3006,
        pageCount = 5572,
        activeUserCount = 14
    ),
    SupportedLanguage(
        code = "zu",
        name = "Zulu",
        localName = "isiZulu",
        articleCount = 11832,
        pageCount = 26184,
        activeUserCount = 47
    ),
)
