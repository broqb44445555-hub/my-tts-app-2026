package com.example.data.model

data class KhmerPhrase(
    val id: String,
    val category: String,
    val categoryKhmer: String,
    val khmerText: String,
    val romanization: String,
    val englishMeaning: String,
    val iconName: String = "chat"
)

object SampleKhmerPhrases {
    val categories = listOf(
        "Greetings" to "ការគួរសម",
        "Daily" to "ប្រចាំថ្ងៃ",
        "Polite" to "ពាក្យគួរសម",
        "Travel" to "ការធ្វើដំណើរ",
        "Food" to "ម្ហូបអាហារ",
        "Shopping" to "ការទិញទំនិញ",
        "Emergency" to "អាសន្ន"
    )

    val phrases = listOf(
        // Greetings
        KhmerPhrase("g1", "Greetings", "ការគួរសម", "ជំរាបសួរ", "Choum reap sour", "Hello (Formal)"),
        KhmerPhrase("g2", "Greetings", "ការគួរសម", "សួស្តី", "Sous-dei", "Hi / Hello (Informal)"),
        KhmerPhrase("g3", "Greetings", "ការគួរសម", "សុខសប្បាយជាទេ?", "Sok sabay te?", "How are you?"),
        KhmerPhrase("g4", "Greetings", "ការគួរសម", "ខ្ញុំសុខសប្បាយជាទេ អរគុណ", "Knhom sok sabay, orkun", "I am fine, thank you"),
        KhmerPhrase("g5", "Greetings", "ការគួរសម", "ជំរាបលា", "Choum reap lea", "Goodbye (Formal)"),
        KhmerPhrase("g6", "Greetings", "ការគួរសម", "រាត្រីសួស្តី", "Reatrey sous-dei", "Good night"),

        // Polite
        KhmerPhrase("p1", "Polite", "ពាក្យគួរសម", "អរគុណច្រើន", "Orkun chroeun", "Thank you very much"),
        KhmerPhrase("p2", "Polite", "ពាក្យគួរសម", "សូមទោស", "Soum tos", "Sorry / Excuse me"),
        KhmerPhrase("p3", "Polite", "ពាក្យគួរសម", "មិនអីទេ", "Mun ey te", "You're welcome / No problem"),
        KhmerPhrase("p4", "Polite", "ពាក្យគួរសម", "សូមជួយខ្ញុំផង", "Soum chuoy knhom phong", "Please help me"),
        KhmerPhrase("p5", "Polite", "ពាក្យគួរសម", "រីករាយណាស់ដែលបានស្គាល់អ្នក", "Rikreay nas dael ban skoal neak", "Pleased to meet you"),

        // Daily
        KhmerPhrase("d1", "Daily", "ប្រចាំថ្ងៃ", "តើអ្នកឈ្មោះអ្វី?", "Te neak chhmous ey?", "What is your name?"),
        KhmerPhrase("d2", "Daily", "ប្រចាំថ្ងៃ", "ខ្ញុំឈ្មោះ...", "Knhom chhmous...", "My name is..."),
        KhmerPhrase("d3", "Daily", "ប្រចាំថ្ងៃ", "តើឥឡូវម៉ោងប៉ុន្មានហើយ?", "Te ilaow maong ponman haeuy?", "What time is it now?"),
        KhmerPhrase("d4", "Daily", "ប្រចាំថ្ងៃ", "ថ្ងៃនេះអាកាសធាតុល្អណាស់", "Thngai nis akas-theat l'or nas", "The weather is very nice today"),
        KhmerPhrase("d5", "Daily", "ប្រចាំថ្ងៃ", "ខ្ញុំស្រលាញ់ប្រទេសកម្ពុជា", "Knhom sralanh bro-tes Kampuchea", "I love Cambodia"),

        // Travel
        KhmerPhrase("t1", "Travel", "ការធ្វើដំណើរ", "តើទៅព្រលានយន្តហោះយ៉ាងម៉េច?", "Te tow prolean yon-hor yang mech?", "How to go to airport?"),
        KhmerPhrase("t2", "Travel", "ការធ្វើដំណើរ", "សូមទៅសណ្ឋាគារនេះ", "Soum tow santhakea nis", "Please take me to this hotel"),
        KhmerPhrase("t3", "Travel", "ការធ្វើដំណើរ", "ឈប់នៅទីនេះ", "Chhob nov ti nis", "Stop here"),
        KhmerPhrase("t4", "Travel", "ការធ្វើដំណើរ", "តើនៅឆ្ងាយប៉ុណ្ណា?", "Te nov chhngey ponna?", "How far is it?"),
        KhmerPhrase("t5", "Travel", "ការធ្វើដំណើរ", "ខ្ញុំចង់ទៅទស្សនាប្រាសាទអង្គរវត្ត", "Knhom chorng tow torsana prasat Angkor Wat", "I want to visit Angkor Wat temple"),

        // Food & Shopping
        KhmerPhrase("f1", "Food", "ម្ហូបអាហារ", "តើមួយនេះថ្លៃប៉ុន្មាន?", "Te mouy nis thlay ponman?", "How much is this one?"),
        KhmerPhrase("f2", "Food", "ម្ហូបអាហារ", "សូមគិតលុយ", "Soum kit luy", "Check the bill, please"),
        KhmerPhrase("f3", "Food", "ម្ហូបអាហារ", "ឆ្ងាញ់ណាស់!", "Chnganh nas!", "Very delicious!"),
        KhmerPhrase("f4", "Food", "ម្ហូបអាហារ", "សូមទឹកសុទ្ធមួយដប", "Soum teuk sot mouy dob", "One bottle of pure water, please"),
        KhmerPhrase("f5", "Food", "ម្ហូបអាហារ", "កុំដាក់ហឹរពេក", "Kom dak her pek", "Not too spicy, please"),

        // Emergency
        KhmerPhrase("e1", "Emergency", "អាសន្ន", "ជួយផង! ជួយផង!", "Chuoy phong! Chuoy phong!", "Help! Help!"),
        KhmerPhrase("e2", "Emergency", "អាសន្ន", "ខ្ញុំត្រូវការគ្រូពេទ្យ", "Knhom trov kar kru-peyt", "I need a doctor"),
        KhmerPhrase("e3", "Emergency", "អាសន្ន", "សូមហៅប៉ូលីស", "Soum hao police", "Please call the police"),
        KhmerPhrase("e4", "Emergency", "អាសន្ន", "ខ្ញុំវង្វេងផ្លូវហើយ", "Knhom vong-veng phlov haeuy", "I am lost")
    )
}
