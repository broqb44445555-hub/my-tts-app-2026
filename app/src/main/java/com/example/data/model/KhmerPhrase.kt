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
        "Proverbs" to "សុភាសិតខ្មែរ",
        "Wisdom" to "ពាក្យស្លោក & ព្រះធម៌",
        "Greetings" to "ការគួរសម",
        "Daily" to "ប្រចាំថ្ងៃ",
        "Polite" to "ពាក្យគួរសម",
        "Travel" to "ការធ្វើដំណើរ",
        "Food" to "ម្ហូបអាហារ & ផ្សារ",
        "Emergency" to "អាសន្ន"
    )

    val phrases = listOf(
        // Khmer Proverbs (សុភាសិតខ្មែរ)
        KhmerPhrase("pv1", "Proverbs", "សុភាសិតខ្មែរ", "ចេះពីរៀន មានពីរក", "Cheh pi rean, mean pi rok", "Knowledge comes from studying, wealth from working"),
        KhmerPhrase("pv2", "Proverbs", "សុភាសិតខ្មែរ", "ចូលស្ទឹងតាមបត់ ចូលស្រុកតាមទេស", "Choul steung tam bot, choul srok tam tes", "Adapt to local customs and situations"),
        KhmerPhrase("pv3", "Proverbs", "សុភាសិតខ្មែរ", "ក្ដៅស៊ីរាក់ ត្រជាក់ស៊ីជ្រៅ", "Kdav si reak, trocheak si chrov", "Patience and gentle calm achieve profound success"),
        KhmerPhrase("pv4", "Proverbs", "សុភាសិតខ្មែរ", "ទូកទៅ កំពង់នៅ", "Touk tow, kom-pong nov", "Boats may depart, but the pier remains steady"),
        KhmerPhrase("pv5", "Proverbs", "សុភាសិតខ្មែរ", "ធ្វើគុណបានទោស ដូចទេសបញ្ជាន់គោ", "Thveu kun ban tos, doch tes banh-chan ko", "Good deeds unappreciated like forced cattle"),
        KhmerPhrase("pv6", "Proverbs", "សុភាសិតខ្មែរ", "កុំទុកចិត្តមេឃ កុំទុកចិត្តផ្កាយ", "Kom touk chet mekh, kom touk chet phkay", "Do not trust the changing sky without preparing"),
        KhmerPhrase("pv7", "Proverbs", "សុភាសិតខ្មែរ", "ស៊ូស្លាប់បា កុំឱ្យស្លាប់មេ ស៊ូលិចទូកកណ្តាលទន្លេ កុំឱ្យតែភ្លើងឆេះផ្ទះ", "Sou slab ba kom oy slab me", "Appreciate the mother's boundless warmth and caring"),

        // Wisdom & Dhamma (ពាក្យស្លោក & ព្រះធម៌)
        KhmerPhrase("w1", "Wisdom", "ពាក្យស្លោក & ព្រះធម៌", "កុសលកម្មនាំមកនូវសេចក្ដីសុខ", "Kosol-kamm noam mok nov sech-kdey sok", "Good karma and virtuous deeds bring peace and joy"),
        KhmerPhrase("w2", "Wisdom", "ពាក្យស្លោក & ព្រះធម៌", "កតញ្ញូជាគុណធម៌ដ៏ខ្ពង់ខ្ពស់របស់មនុស្ស", "Katanyu chea kun-thor dor khpong-khpos", "Gratitude is the highest moral virtue of humanity"),
        KhmerPhrase("w3", "Wisdom", "ពាក្យស្លោក & ព្រះធម៌", "ចិត្តជាមេ ចិត្តជាធំ អ្វីៗសម្រេចមកពីចិត្ត", "Chet chea me, chet chea thom", "The mind is the forerunner; everything is led by the mind"),
        KhmerPhrase("w4", "Wisdom", "ពាក្យស្លោក & ព្រះធម៌", "ការអត់ធ្មត់ជាគ្រឿងលម្អរបស់បណ្ឌិត", "Kar ot-thmot chea kreung l'or robos bandit", "Patience is the ornament of the wise person"),
        KhmerPhrase("w5", "Wisdom", "ពាក្យស្លោក & ព្រះធម៌", "សូមឱ្យសត្វលោកទាំងអស់បានសុខសាន្ត", "Soum oy sat-lok teang-os ban sok-san", "May all living beings be peaceful and free from suffering"),

        // Greetings
        KhmerPhrase("g1", "Greetings", "ការគួរសម", "ជំរាបសួរ", "Choum reap sour", "Hello (Formal / Traditional)"),
        KhmerPhrase("g2", "Greetings", "ការគួរសម", "សួស្តី", "Sous-dei", "Hi / Hello (Friendly)"),
        KhmerPhrase("g3", "Greetings", "ការគួរសម", "សុខសប្បាយជាទេ?", "Sok sabay te?", "How are you doing?"),
        KhmerPhrase("g4", "Greetings", "ការគួរសម", "ខ្ញុំសុខសប្បាយជាទេ អរគុណច្រើន", "Knhom sok sabay, orkun chroeun", "I am doing well, thank you very much"),
        KhmerPhrase("g5", "Greetings", "ការគួរសម", "ជំរាបលា", "Choum reap lea", "Goodbye (Formal / Traditional)"),
        KhmerPhrase("g6", "Greetings", "ការគួរសម", "រាត្រីសួស្តី សូមសុបិនល្អ", "Reatrey sous-dei, soum soben l'or", "Good night, have sweet dreams"),

        // Polite
        KhmerPhrase("p1", "Polite", "ពាក្យគួរសម", "អរគុណច្រើន", "Orkun chroeun", "Thank you very much"),
        KhmerPhrase("p2", "Polite", "ពាក្យគួរសម", "សូមទោស", "Soum tos", "Sorry / Excuse me"),
        KhmerPhrase("p3", "Polite", "ពាក្យគួរសម", "មិនអីទេ មិនបាច់គិតទេ", "Mun ey te, mun bach kit te", "You're welcome / No trouble at all"),
        KhmerPhrase("p4", "Polite", "ពាក្យគួរសម", "សូមជួយខ្ញុំបន្តិចមក", "Soum chuoy knhom bon-tech mok", "Could you please help me for a moment?"),
        KhmerPhrase("p5", "Polite", "ពាក្យគួរសម", "រីករាយណាស់ដែលបានស្គាល់លោកអ្នក", "Rikreay nas dael ban skoal neak", "Honored and pleased to meet you"),

        // Daily
        KhmerPhrase("d1", "Daily", "ប្រចាំថ្ងៃ", "តើអ្នកឈ្មោះអ្វី?", "Te neak chhmous ey?", "What is your name?"),
        KhmerPhrase("d2", "Daily", "ប្រចាំថ្ងៃ", "ខ្ញុំស្រលាញ់ប្រទេសកម្ពុជា", "Knhom sralanh bro-tes Kampuchea", "I love the Kingdom of Cambodia"),
        KhmerPhrase("d3", "Daily", "ប្រចាំថ្ងៃ", "តើឥឡូវម៉ោងប៉ុន្មានហើយ?", "Te ilaow maong ponman haeuy?", "What time is it right now?"),
        KhmerPhrase("d4", "Daily", "ប្រចាំថ្ងៃ", "ថ្ងៃនេះអាកាសធាតុត្រជាក់ស្រួលណាស់", "Thngai nis akas-theat trocheak sruol", "The weather is very pleasantly cool today"),

        // Travel
        KhmerPhrase("t1", "Travel", "ការធ្វើដំណើរ", "ខ្ញុំចង់ទៅទស្សនាប្រាសាទអង្គរវត្ត", "Knhom chorng tow torsana prasat Angkor Wat", "I want to visit the magnificent Angkor Wat temple"),
        KhmerPhrase("t2", "Travel", "ការធ្វើដំណើរ", "តើទៅព្រលានយន្តហោះសៀមរាបអង្គរយ៉ាងដូចម្តេច?", "Te tow prolean yon-hor Siem Reap yang doch-mdech?", "How do I get to Siem Reap Angkor International Airport?"),
        KhmerPhrase("t3", "Travel", "ការធ្វើដំណើរ", "សូមឈប់នៅទីនេះ", "Soum chhob nov ti nis", "Please stop right here"),

        // Food & Shopping
        KhmerPhrase("f1", "Food", "ម្ហូបអាហារ & ផ្សារ", "តើម្ហូបនេះថ្លៃប៉ុន្មានដែរ?", "Te mhoub nis thlay ponman daer?", "How much does this dish cost?"),
        KhmerPhrase("f2", "Food", "ម្ហូបអាហារ & ផ្សារ", "សូមគិតលុយផង", "Soum kit luy phong", "Please check the bill / Check please"),
        KhmerPhrase("f3", "Food", "ម្ហូបអាហារ & ផ្សារ", "ម្ហូបនេះពិតជាឆ្ងាញ់ពិសាណាស់", "Mhoub nis pit chea chnganh pisa nas", "This Cambodian food is genuinely delicious!"),
        KhmerPhrase("f4", "Food", "ម្ហូបអាហារ & ផ្សារ", "សូមបញ្ចុះតម្លៃបន្តិចបានទេ?", "Soum banh-choh thlay bon-tech ban te?", "Could you give a little discount, please?"),

        // Emergency
        KhmerPhrase("e1", "Emergency", "អាសន្ន", "ជួយផង! ជួយផង!", "Chuoy phong! Chuoy phong!", "Help! Help!"),
        KhmerPhrase("e2", "Emergency", "អាសន្ន", "ខ្ញុំត្រូវការគ្រូពេទ្យជាបន្ទាន់", "Knhom trov kar kru-peyt chea bon-toan", "I need a doctor urgently"),
        KhmerPhrase("e3", "Emergency", "អាសន្ន", "សូមជួយទាក់ទងមន្ទីរពេទ្យ", "Soum chuoy teak-tong montei-peyt", "Please help contact the hospital")
    )
}
