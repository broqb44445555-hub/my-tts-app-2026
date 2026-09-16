package com.example.data.model

data class KhmerConsonant(
    val char: String,
    val name: String,
    val series: String, // "ពួក អ" or "ពួក អ៊"
    val sampleWord: String,
    val phonetic: String,
    val speechText: String
)

data class KhmerVowel(
    val char: String,
    val name: String,
    val exampleWithK: String,
    val phonetic: String,
    val speechText: String
)

object KhmerAlphabetData {
    val consonants = listOf(
        // ពួក អ (First Series)
        KhmerConsonant("ក", "ក (ក-ក្អែក)", "ពួក អ", "ក្អែក", "Kâ", "ក ក្អែក"),
        KhmerConsonant("ខ", "ខ (ខ-ខោ)", "ពួក អ", "ខោ", "Khâ", "ខ ខោ"),
        KhmerConsonant("គ", "គ (គ-គោ)", "ពួក អ៊", "គោ", "Kô", "គ គោ"),
        KhmerConsonant("ឃ", "ឃ (ឃ-ឃ្មុំ)", "ពួក អ៊", "ឃ្មុំ", "Khô", "ឃ ឃ្មុំ"),
        KhmerConsonant("ង", "ង (ង-ងាវ)", "ពួក អ៊", "ងាវ", "Ngô", "ង ងាវ"),

        KhmerConsonant("ច", "ច (ច-ចាន)", "ពួក អ", "ចាន", "Châ", "ច ចាន"),
        KhmerConsonant("ឆ", "ឆ (ឆ-ឆ័ត្រ)", "ពួក អ", "ឆ័ត្រ", "Chhâ", "ឆ ឆ័ត្រ"),
        KhmerConsonant("ជ", "ជ (ជ-ជើង)", "ពួក អ៊", "ជើង", "Chô", "ជ ជើង"),
        KhmerConsonant("ឈ", "ឈ (ឈ-ឈើ)", "ពួក អ៊", "ឈើ", "Chhô", "ឈ ឈើ"),
        KhmerConsonant("ញ", "ញ (ញ-ញញួរ)", "ពួក អ៊", "ញញួរ", "Nhô", "ញ ញញួរ"),

        KhmerConsonant("ដ", "ដ (ដ-ដំរី)", "ពួក អ", "ដំរី", "Dâ", "ដ ដំរី"),
        KhmerConsonant("ឋ", "ឋ (ឋ-ឋាន)", "ពួក អ", "ឋាន", "Thâ", "ឋ ឋាន"),
        KhmerConsonant("ឌ", "ឌ (ឌ-ឌុក)", "ពួក អ៊", "ឌុក", "Dô", "ឌ ឌុក"),
        KhmerConsonant("ឍ", "ឍ (ឍ-ឍាន)", "ពួក អ៊", "ឍាន", "Thô", "ឍ ឍាន"),
        KhmerConsonant("ណ", "ណ (ណ-ណែនាំ)", "ពួក អ", "ណែនាំ", "Nâ", "ណ ណែនាំ"),

        KhmerConsonant("ត", "ត (ត-តា)", "ពួក អ", "តា", "Tâ", "ត តា"),
        KhmerConsonant("ថ", "ថ (ថ-ថង់)", "ពួក អ", "ថង់", "Thâ", "ថ ថង់"),
        KhmerConsonant("ទ", "ទ (ទ-ទា)", "ពួក អ៊", "ទា", "Tô", "ទ ទា"),
        KhmerConsonant("ធ", "ធ (ធ-ធុង)", "ពួក អ៊", "ធុង", "Thô", "ធ ធុង"),
        KhmerConsonant("ន", "ន (ន-នំ)", "ពួក អ៊", "នំ", "Nô", "ន នំ"),

        KhmerConsonant("ប", "ប (ប-បក្សី)", "ពួក អ", "បក្សី", "Bâ", "ប បក្សី"),
        KhmerConsonant("ផ", "ផ (ផ-ផ្លែឈើ)", "ពួក អ", "ផ្លែឈើ", "Phâ", "ផ ផ្លែឈើ"),
        KhmerConsonant("ព", "ព (ព-ពពក)", "ពួក អ៊", "ពពក", "Pô", "ព ពពក"),
        KhmerConsonant("ភ", "ភ (ភ-ភ្នំ)", "ពួក អ៊", "ភ្នំ", "Phô", "ភ ភ្នំ"),
        KhmerConsonant("ម", "ម (ម-មាន់)", "ពួក អ៊", "មាន់", "Mô", "ម មាន់"),

        KhmerConsonant("យ", "យ (យ-យក្ស)", "ពួក អ៊", "យក្ស", "Yô", "យ យក្ស"),
        KhmerConsonant("រ", "រ (រ-រទេះ)", "ពួក អ៊", "រទេះ", "Rô", "រ រទេះ"),
        KhmerConsonant("ល", "ល (ល-លុយ)", "ពួក អ៊", "លុយ", "Lô", "ល លុយ"),
        KhmerConsonant("វ", "វ (វ-វែនតា)", "ពួក អ៊", "វែនតា", "Vô", "វ វែនតា"),

        KhmerConsonant("ស", "ស (ស-សេះ)", "ពួក អ", "សេះ", "Sâ", "ស សេះ"),
        KhmerConsonant("ហ", "ហ (ហ-ហោះ)", "ពួក អ", "ហោះ", "Hâ", "ហ ហោះ"),
        KhmerConsonant("ឡ", "ឡ (ឡ-ឡាន)", "ពួក អ", "ឡាន", "Lâ", "ឡ ឡាន"),
        KhmerConsonant("អ", "អ (អ-អក្សរ)", "ពួក អ", "អក្សរ", "Â", "អ អក្សរ")
    )

    val vowels = listOf(
        KhmerVowel("ា", "ស្រៈ អា", "កា", "Ah", "ស្រៈ អា"),
        KhmerVowel("ិ", "ស្រៈ អិ", "កិ", "Eh / I", "ស្រៈ អិ"),
        KhmerVowel("ី", "ស្រៈ អី", "កី", "Ey / Ee", "ស្រៈ អី"),
        KhmerVowel("ឹ", "ស្រៈ អ៊ឹ", "កឹ", "Oe", "ស្រៈ អ៊ឹ"),
        KhmerVowel("ឺ", "ស្រៈ អ៊ឺ", "កឺ", "Oeu", "ស្រៈ អ៊ឺ"),
        KhmerVowel("ុ", "ស្រៈ អុ", "កុ", "Oo", "ស្រៈ អុ"),
        KhmerVowel("ូ", "ស្រៈ អ៊ូ", "កូ", "Oov", "ស្រៈ អ៊ូ"),
        KhmerVowel("ួ", "ស្រៈ អួ", "កួ", "Uo", "ស្រៈ អួ"),
        KhmerVowel("ើ", "ស្រៈ អើ", "កើ", "Aeur", "ស្រៈ អើ"),
        KhmerVowel("ឿ", "ស្រៈ អឿ", "កឿ", "Eua", "ស្រៈ អឿ"),
        KhmerVowel("ៀ", "ស្រៈ អៀ", "កៀ", "Ie", "ស្រៈ អៀ"),
        KhmerVowel("េ", "ស្រៈ អេ", "កេ", "Ay", "ស្រៈ អេ"),
        KhmerVowel("ែ", "ស្រៈ អែ", "កែ", "Ae", "ស្រៈ អែ"),
        KhmerVowel("ៃ", "ស្រៈ អៃ", "កៃ", "Ay", "ស្រៈ អៃ"),
        KhmerVowel("ោ", "ស្រៈ អោ", "កោ", "Ao", "ស្រៈ អោ"),
        KhmerVowel("ៅ", "ស្រៈ អៅ", "កៅ", "Av / Ow", "ស្រៈ អៅ"),
        KhmerVowel("ុំ", "ស្រៈ អុំ", "កុំ", "Om", "ស្រៈ អុំ"),
        KhmerVowel("ំ", "ស្រៈ អំ", "កំ", "Am", "ស្រៈ អំ"),
        KhmerVowel("ាំ", "ស្រៈ អាំ", "កាំ", "Aam", "ស្រៈ អាំ"),
        KhmerVowel("ះ", "ស្រៈ អះ", "កះ", "Ah", "ស្រៈ អះ")
    )
}
