package com.example.data

import com.example.model.FoodCategory
import com.example.model.FoodItem
import com.example.model.ServingOption

object FoodDatabase {
    val foods: List<FoodItem> = listOf(
        // BANGLA & SOUTH ASIAN STAPLES
        FoodItem(
            id = "rice_white",
            nameEn = "Boiled White Rice (Sada Bhat)",
            nameBn = "সাদা ভাত",
            category = FoodCategory.BANGLA_STAPLE,
            defaultCaloriesPer100g = 130,
            emoji = "🍚",
            descriptionEn = "Steamed plain rice, everyday South Asian staple",
            descriptionBn = "দৈনন্দিন সাধারণ রান্না করা ভাত",
            carbsGramsPer100g = 28.0,
            proteinGramsPer100g = 2.7,
            fatGramsPer100g = 0.3,
            servingOptions = listOf(
                ServingOption("1 Cup (150g)", "১ কাপ (১৫০ গ্রাম)", 150, 195, isDefault = true),
                ServingOption("1 Plate / Thali (250g)", "১ থালা ভাত (২৫০ গ্রাম)", 250, 325),
                ServingOption("Half Cup (75g)", "হাফ কাপ (৭৫ গ্রাম)", 75, 98),
                ServingOption("Small Bowl (120g)", "ছোট বাটি (১২০ গ্রাম)", 120, 156)
            )
        ),
        FoodItem(
            id = "roti_chapati",
            nameEn = "Atta Roti / Chapati",
            nameBn = "আটার রুটি / চাপাত্তি",
            category = FoodCategory.BANGLA_STAPLE,
            defaultCaloriesPer100g = 297,
            emoji = "🫓",
            descriptionEn = "Whole wheat flatbread cooked dry on tawa",
            descriptionBn = "তাওয়ায় সেঁকা লাল আটার পাতলা রুটি",
            carbsGramsPer100g = 47.0,
            proteinGramsPer100g = 9.0,
            fatGramsPer100g = 3.5,
            servingOptions = listOf(
                ServingOption("1 Medium Roti (35g)", "১টি মাঝারি রুটি (৩৫ গ্রাম)", 35, 85, isDefault = true),
                ServingOption("2 Rotis (70g)", "২টি রুটি (৭০ গ্রাম)", 70, 170),
                ServingOption("3 Rotis (105g)", "৩টি রুটি (১০৫ গ্রাম)", 105, 255),
                ServingOption("1 Large Roti (50g)", "১টি বড় রুটি (৫০ গ্রাম)", 50, 120)
            )
        ),
        FoodItem(
            id = "paratha_oil",
            nameEn = "Plain Paratha (Shadha Parota)",
            nameBn = "তেলে ভাজা পরোটা",
            category = FoodCategory.BANGLA_STAPLE,
            defaultCaloriesPer100g = 360,
            emoji = "🫓",
            descriptionEn = "Layered flatbread fried in oil or ghee",
            descriptionBn = "তেল বা ঘিয়ে ভাজা স্তরযুক্ত পরোটা",
            carbsGramsPer100g = 45.0,
            proteinGramsPer100g = 6.0,
            fatGramsPer100g = 16.0,
            servingOptions = listOf(
                ServingOption("1 Medium Paratha (75g)", "১টি পরোটা (৭৫ গ্রাম)", 75, 260, isDefault = true),
                ServingOption("2 Parathas (150g)", "২টি পরোটা (১৫০ গ্রাম)", 150, 520),
                ServingOption("1 Large Mughlai Paratha (180g)", "১টি মোগলাই পরোটা (১৮০ গ্রাম)", 180, 480)
            )
        ),
        FoodItem(
            id = "biryani_chicken",
            nameEn = "Chicken Biryani / Morog Polao",
            nameBn = "চিকেন বিরিয়ানি / মোরগ পোলাও",
            category = FoodCategory.BANGLA_STAPLE,
            defaultCaloriesPer100g = 180,
            emoji = "🍗",
            descriptionEn = "Aromatic spiced rice cooked with chicken piece & ghee",
            descriptionBn = "ঘি, মসলা ও মুরগির মাংস মিশ্রিত সুস্বাদু বিরিয়ানি",
            carbsGramsPer100g = 22.0,
            proteinGramsPer100g = 9.5,
            fatGramsPer100g = 6.0,
            servingOptions = listOf(
                ServingOption("1 Standard Plate (350g)", "১ প্লেট (৩৫০ গ্রাম)", 350, 550, isDefault = true),
                ServingOption("1 Full Rich Serving (450g)", "১ বড় প্লেট (৪৫০ গ্রাম)", 450, 720),
                ServingOption("Half Plate (200g)", "হাফ প্লেট (২০০ গ্রাম)", 200, 320)
            )
        ),
        FoodItem(
            id = "biryani_kacchi_mutton",
            nameEn = "Kacchi Biryani (Mutton / Beef)",
            nameBn = "কাচ্চি বিরিয়ানি (খাসি / গরুর)",
            category = FoodCategory.BANGLA_STAPLE,
            defaultCaloriesPer100g = 210,
            emoji = "🥩",
            descriptionEn = "Rich fragrant basmati/chinigura rice with marinated tender meat and potato",
            descriptionBn = "আলু, ঘি ও সুগন্ধি চালে রান্না ঐতিহ্যবাহী কাচ্চি",
            carbsGramsPer100g = 24.0,
            proteinGramsPer100g = 10.0,
            fatGramsPer100g = 9.0,
            servingOptions = listOf(
                ServingOption("1 Standard Plate (400g)", "১ প্লেট কাচ্চি (৪০০ গ্রাম)", 400, 750, isDefault = true),
                ServingOption("1 Large Plate with Aloo (500g)", "১ বড় প্লেট আলুসহ (৫০০ গ্রাম)", 500, 920),
                ServingOption("Half Plate (220g)", "হাফ প্লেট (২২০ গ্রাম)", 220, 420)
            )
        ),
        FoodItem(
            id = "khichuri_bhuna",
            nameEn = "Bhuna Khichuri (Rice & Lentils)",
            nameBn = "ভুনা খিচুড়ি",
            category = FoodCategory.BANGLA_STAPLE,
            defaultCaloriesPer100g = 165,
            emoji = "🍲",
            descriptionEn = "Spiced dry cooked rice and roasted moong dal with mustard oil",
            descriptionBn = "ভাজা মুগডাল ও চালের সুস্বাদু ভুনা খিচুড়ি",
            carbsGramsPer100g = 25.0,
            proteinGramsPer100g = 5.5,
            fatGramsPer100g = 4.5,
            servingOptions = listOf(
                ServingOption("1 Plate (300g)", "১ প্লেট (৩০০ গ্রাম)", 300, 480, isDefault = true),
                ServingOption("1 Bowl (200g)", "১ বাটি (২০০ গ্রাম)", 200, 320),
                ServingOption("Half Plate (150g)", "হাফ প্লেট (১৫০ গ্রাম)", 150, 240)
            )
        ),
        FoodItem(
            id = "polao_plain",
            nameEn = "Plain Polao (Ghee Bhat)",
            nameBn = "সাদা পোলাও",
            category = FoodCategory.BANGLA_STAPLE,
            defaultCaloriesPer100g = 185,
            emoji = "🍚",
            descriptionEn = "Fragrant chinigura rice cooked in light ghee and whole aromatics",
            descriptionBn = "ঘি ও সুগন্ধি চালে রান্না সাদা পোলাও",
            carbsGramsPer100g = 27.0,
            proteinGramsPer100g = 3.5,
            fatGramsPer100g = 6.5,
            servingOptions = listOf(
                ServingOption("1 Plate (250g)", "১ প্লেট (২৫০ গ্রাম)", 250, 440, isDefault = true),
                ServingOption("1 Cup (150g)", "১ কাপ (১৫০ গ্রাম)", 150, 270)
            )
        ),
        FoodItem(
            id = "naan_tandoori",
            nameEn = "Tandoori Naan / Butter Naan",
            nameBn = "তন্দুরি নান / বাটার নান",
            category = FoodCategory.BANGLA_STAPLE,
            defaultCaloriesPer100g = 310,
            emoji = "🫓",
            descriptionEn = "Leavened oven-baked flatbread from tandoor",
            descriptionBn = "তন্দুরে সেঁকা তুলতুলে নান রুটি",
            carbsGramsPer100g = 50.0,
            proteinGramsPer100g = 8.0,
            fatGramsPer100g = 7.0,
            servingOptions = listOf(
                ServingOption("1 Plain Naan (100g)", "১টি সাধারণ নান (১০০ গ্রাম)", 100, 280, isDefault = true),
                ServingOption("1 Butter Naan (110g)", "১টি বাটার নান (১১০ গ্রাম)", 110, 340),
                ServingOption("1 Garlic Naan (115g)", "১টি গার্লিক নান (১১৫ গ্রাম)", 115, 360)
            )
        ),

        // DAL & LENTILS
        FoodItem(
            id = "dal_masoor_patla",
            nameEn = "Patla Masoor Dal (Thin Red Lentil Soup)",
            nameBn = "পাতলা মসুর ডাল",
            category = FoodCategory.DAL_LENTILS,
            defaultCaloriesPer100g = 55,
            emoji = "🥣",
            descriptionEn = "Light everyday red lentil broth with garlic-onion baghar",
            descriptionBn = "রসুন-পেঁয়াজের বাঘার দেওয়া পাতলা মসুর ডাল",
            carbsGramsPer100g = 7.5,
            proteinGramsPer100g = 3.8,
            fatGramsPer100g = 1.2,
            servingOptions = listOf(
                ServingOption("1 Medium Bowl (180ml)", "১ মাঝারি বাটি (১৮০ মিলি)", 180, 95, isDefault = true),
                ServingOption("1 Small Katori (120ml)", "১ ছোট বাটি (১২০ মিলি)", 120, 65),
                ServingOption("1 Large Bowl (250ml)", "১ বড় বাটি (২৫০ মিলি)", 250, 135)
            )
        ),
        FoodItem(
            id = "dal_ghono_moong",
            nameEn = "Bhuna Moong Dal (Thick Yellow Lentils)",
            nameBn = "ভুনা মুগ ডাল",
            category = FoodCategory.DAL_LENTILS,
            defaultCaloriesPer100g = 125,
            emoji = "🥣",
            descriptionEn = "Rich thick yellow roasted moong dal with spices and ghee",
            descriptionBn = "ঘিয়ে সাঁতলানো ঘন সুস্বাদু মুগ ডাল",
            carbsGramsPer100g = 16.0,
            proteinGramsPer100g = 7.0,
            fatGramsPer100g = 4.0,
            servingOptions = listOf(
                ServingOption("1 Bowl (150g)", "১ বাটি (১৫০ গ্রাম)", 150, 180, isDefault = true),
                ServingOption("Half Bowl (80g)", "হাফ বাটি (৮০ গ্রাম)", 80, 95)
            )
        ),
        FoodItem(
            id = "chhola_boot_bhuna",
            nameEn = "Chhola Bhuna / Booter Dal",
            nameBn = "ছোলা ভুনা / বুটের ডাল",
            category = FoodCategory.DAL_LENTILS,
            defaultCaloriesPer100g = 165,
            emoji = "🥘",
            descriptionEn = "Spiced cooked chickpeas or Bengal gram with onions & spices",
            descriptionBn = "ইফতার ও নাস্তার ঐতিহ্যবাহী মশলাদার ছোলা ভুনা",
            carbsGramsPer100g = 22.0,
            proteinGramsPer100g = 7.5,
            fatGramsPer100g = 5.0,
            servingOptions = listOf(
                ServingOption("1 Small Plate / Bowl (150g)", "১ বাটি / প্লেইট (১৫০ গ্রাম)", 150, 240, isDefault = true),
                ServingOption("Half Cup (100g)", "হাফ কাপ (১০০ গ্রাম)", 100, 160),
                ServingOption("Large Serving (220g)", "বড় পরিবেশন (২২০ গ্রাম)", 220, 350)
            )
        ),

        // CURRY, FISH & MEAT
        FoodItem(
            id = "hilsa_curry",
            nameEn = "Ilish Macher Jhol / Shorshe Ilish",
            nameBn = "ইলিশ মাছের ঝোল / সর্ষে ইলিশ",
            category = FoodCategory.CURRY_FISH_MEAT,
            defaultCaloriesPer100g = 230,
            emoji = "🐟",
            descriptionEn = "National fish of Bangladesh in mustard paste or light turmeric broth",
            descriptionBn = "সর্ষে বা হালকা মশলায় রান্না বাংলাদেশের জাতীয় ইলিশ মাছ",
            carbsGramsPer100g = 2.0,
            proteinGramsPer100g = 16.5,
            fatGramsPer100g = 17.0,
            servingOptions = listOf(
                ServingOption("1 Piece with Gravy (120g)", "১ টুকরা ঝোলসহ (১২০ গ্রাম)", 120, 255, isDefault = true),
                ServingOption("1 Fried Piece / Bhaja (90g)", "১ টুকরা ভাজা ইলিশ (৯০ গ্রাম)", 90, 210),
                ServingOption("2 Pieces with Gravy (220g)", "২ টুকরা ঝোলসহ (২২০ গ্রাম)", 220, 470)
            )
        ),
        FoodItem(
            id = "rui_mach_jhol",
            nameEn = "Rui / Katla Macher Kalia or Jhol",
            nameBn = "রুই মাছের ঝোল / কালিয়া",
            category = FoodCategory.CURRY_FISH_MEAT,
            defaultCaloriesPer100g = 155,
            emoji = "🐟",
            descriptionEn = "Rohu carp fish cooked in traditional spiced onion-ginger gravy",
            descriptionBn = "পেঁয়াজ, আদা ও মশলার ঝোলে রান্না রুই মাছ",
            carbsGramsPer100g = 3.0,
            proteinGramsPer100g = 17.0,
            fatGramsPer100g = 8.0,
            servingOptions = listOf(
                ServingOption("1 Medium Piece with Gravy (120g)", "১ টুকরা ঝোলসহ (১২০ গ্রাম)", 120, 180, isDefault = true),
                ServingOption("1 Fried Rui Fish Piece (80g)", "১ টুকরা ভাজা রুই (৮০ গ্রাম)", 80, 145),
                ServingOption("2 Pieces (220g)", "২ টুকরা মাছ (২২০ গ্রাম)", 220, 330)
            )
        ),
        FoodItem(
            id = "chicken_curry_bangla",
            nameEn = "Bangla Murgir Mangsho (Chicken Curry with Potato)",
            nameBn = "আলু দিয়ে মুরগির মাংসের ঝোল",
            category = FoodCategory.CURRY_FISH_MEAT,
            defaultCaloriesPer100g = 170,
            emoji = "🍗",
            descriptionEn = "Home-style chicken curry cooked with potatoes and whole spices",
            descriptionBn = "বাঙালি স্টাইলের আলু ও মুরগির মাংসের পাতলা ঝোল",
            carbsGramsPer100g = 4.5,
            proteinGramsPer100g = 16.0,
            fatGramsPer100g = 9.5,
            servingOptions = listOf(
                ServingOption("1 Bowl (2 Pieces + 1 Potato + Gravy, 200g)", "১ বাটি (২ পিস + আলু + ঝোল, ২০০ গ্রাম)", 200, 310, isDefault = true),
                ServingOption("1 Piece with Gravy (100g)", "১ পিস ঝোলসহ (১০০ গ্রাম)", 100, 160),
                ServingOption("Large Serving (3 Pieces + Potato, 300g)", "বড় বাটি (৩ পিস + আলু, ৩০০ গ্রাম)", 300, 460)
            )
        ),
        FoodItem(
            id = "beef_bhuna_bangla",
            nameEn = "Gorur Mangsho Bhuna (Spicy Beef Bhuna)",
            nameBn = "গরুর মাংস ভুনা",
            category = FoodCategory.CURRY_FISH_MEAT,
            defaultCaloriesPer100g = 250,
            emoji = "🥩",
            descriptionEn = "Slow-cooked tender beef chuck in dark aromatic spicy thick gravy",
            descriptionBn = "কষানো ও মশলাদার ঐতিহ্যবাহী গরুর মাংস ভুনা",
            carbsGramsPer100g = 3.0,
            proteinGramsPer100g = 22.0,
            fatGramsPer100g = 16.5,
            servingOptions = listOf(
                ServingOption("1 Small Bowl (3-4 Pieces, 150g)", "১ ছোট বাটি (৩-৪ টুকরা, ১৫০ গ্রাম)", 150, 360, isDefault = true),
                ServingOption("1 Standard Serving (200g)", "১ মাঝারি বাটি (২০০ গ্রাম)", 200, 480),
                ServingOption("Mezbani Beef Serving (180g)", "মেজ্জানি গোশত পরিবেশন (১৮০ গ্রাম)", 180, 440)
            )
        ),
        FoodItem(
            id = "mutton_curry_khasir",
            nameEn = "Khasir Mangsho (Mutton / Goat Curry)",
            nameBn = "খাসির মাংসের ঝোল",
            category = FoodCategory.CURRY_FISH_MEAT,
            defaultCaloriesPer100g = 240,
            emoji = "🍲",
            descriptionEn = "Tender goat meat slow-simmered with onions, garlic, and garam masala",
            descriptionBn = "গরম মশলায় কষিয়ে রান্না খাসির মাংস",
            carbsGramsPer100g = 2.5,
            proteinGramsPer100g = 20.0,
            fatGramsPer100g = 16.0,
            servingOptions = listOf(
                ServingOption("1 Medium Bowl (160g)", "১ মাঝারি বাটি (১৬০ গ্রাম)", 160, 370, isDefault = true),
                ServingOption("Large Serving (250g)", "১ বড় বাটি (২৫০ গ্রাম)", 250, 580)
            )
        ),
        FoodItem(
            id = "egg_curry_dim_bhuna",
            nameEn = "Dim Bhuna / Egg Korma",
            nameBn = "ডিম ভুনা / ডিমের কোরমা",
            category = FoodCategory.CURRY_FISH_MEAT,
            defaultCaloriesPer100g = 185,
            emoji = "🥚",
            descriptionEn = "Hard boiled hen egg shallow fried and simmered in onion masala gravy",
            descriptionBn = "তেলে ভেজে পেঁয়াজ মশলায় কষানো ডিম ভুনা",
            carbsGramsPer100g = 3.5,
            proteinGramsPer100g = 11.5,
            fatGramsPer100g = 13.0,
            servingOptions = listOf(
                ServingOption("1 Egg with Gravy (100g)", "১টি ডিম ঝোলসহ (১০০ গ্রাম)", 100, 180, isDefault = true),
                ServingOption("2 Eggs with Gravy (180g)", "২টি ডিম ঝোলসহ (১৮০ গ্রাম)", 180, 330)
            )
        ),
        FoodItem(
            id = "egg_boiled_hen",
            nameEn = "Boiled Egg (Hard Boiled)",
            nameBn = "সিদ্ধ ডিম",
            category = FoodCategory.CURRY_FISH_MEAT,
            defaultCaloriesPer100g = 155,
            emoji = "🥚",
            descriptionEn = "Whole boiled chicken egg",
            descriptionBn = "পানিতে সিদ্ধ আস্ত মুরগির ডিম",
            carbsGramsPer100g = 1.1,
            proteinGramsPer100g = 13.0,
            fatGramsPer100g = 11.0,
            servingOptions = listOf(
                ServingOption("1 Large Boiled Egg (50g)", "১টি সিদ্ধ ডিম (৫০ গ্রাম)", 50, 78, isDefault = true),
                ServingOption("2 Boiled Eggs (100g)", "২টি সিদ্ধ ডিম (১০০ গ্রাম)", 100, 155),
                ServingOption("1 Egg White Only (33g)", "১টি ডিমের শুধু সাদা অংশ (৩৩ গ্রাম)", 33, 17)
            )
        ),
        FoodItem(
            id = "egg_omlet_bangla",
            nameEn = "Bangla Dim Bhaja (Egg Omelette with Onion & Green Chilli)",
            nameBn = "ডিম ভাজা (পেঁয়াজ-মরিচ ওমলেট)",
            category = FoodCategory.CURRY_FISH_MEAT,
            defaultCaloriesPer100g = 220,
            emoji = "🍳",
            descriptionEn = "Whisked egg with diced onions and green chilies fried in mustard oil",
            descriptionBn = "পেঁয়াজ ও কাঁচা মরিচ দিয়ে তেলে ভাজা ডিম",
            carbsGramsPer100g = 2.0,
            proteinGramsPer100g = 12.0,
            fatGramsPer100g = 17.5,
            servingOptions = listOf(
                ServingOption("1 Egg Omelette (65g)", "১টি ডিমের ওমলেট (৬৫ গ্রাম)", 65, 140, isDefault = true),
                ServingOption("2 Eggs Omelette (120g)", "২টি ডিমের ডাবল ওমলেট (১২০ গ্রাম)", 120, 260)
            )
        ),
        FoodItem(
            id = "shrimp_chingri_malaikari",
            nameEn = "Chingri Malai Curry (Prawn Coconut Curry)",
            nameBn = "চিংড়ি মালাইকারি",
            category = FoodCategory.CURRY_FISH_MEAT,
            defaultCaloriesPer100g = 190,
            emoji = "🍤",
            descriptionEn = "Succulent prawns cooked in creamy coconut milk and mild spices",
            descriptionBn = "নারকেলের দুধে রান্না সুস্বাদু গলদা বা বাগদা চিংড়ি",
            carbsGramsPer100g = 4.0,
            proteinGramsPer100g = 14.0,
            fatGramsPer100g = 12.0,
            servingOptions = listOf(
                ServingOption("1 Portion (150g)", "১ পরিবেশন (১৫০ গ্রাম)", 150, 275, isDefault = true),
                ServingOption("Small Bowl (100g)", "ছোট বাটি (১০০ গ্রাম)", 100, 185)
            )
        ),

        // VEGETABLES & BHAJI & BHORTA
        FoodItem(
            id = "aloo_bhaji",
            nameEn = "Aloo Bhaji (Sliced Potato Fry)",
            nameBn = "আলু ভাজি",
            category = FoodCategory.VEGETABLES,
            defaultCaloriesPer100g = 175,
            emoji = "🥔",
            descriptionEn = "Crisp and soft thinly shredded potato fried with turmeric & onion",
            descriptionBn = "সকালের নাস্তায় রুটির সাথে জনপ্রিয় আলু ভাজি",
            carbsGramsPer100g = 22.0,
            proteinGramsPer100g = 2.5,
            fatGramsPer100g = 8.5,
            servingOptions = listOf(
                ServingOption("1 Serving for Roti (80g)", "১ পরিবেশন (৮০ গ্রাম)", 80, 140, isDefault = true),
                ServingOption("1 Small Cup (120g)", "১ বাটি (১২০ গ্রাম)", 120, 210),
                ServingOption("Half Serving (50g)", "হাফ পরিবেশন (৫০ গ্রাম)", 50, 88)
            )
        ),
        FoodItem(
            id = "aloo_bhorta",
            nameEn = "Aloo Bhorta (Mashed Potato with Mustard Oil & Fried Chili)",
            nameBn = "আলু ভর্তা",
            category = FoodCategory.VEGETABLES,
            defaultCaloriesPer100g = 135,
            emoji = "🥔",
            descriptionEn = "Boiled potatoes mashed with mustard oil, dried red chilies and onion",
            descriptionBn = "সরিষার তেল ও শুকনা মরিচ দিয়ে মাখানো বাঙালি আলু ভর্তা",
            carbsGramsPer100g = 18.0,
            proteinGramsPer100g = 2.0,
            fatGramsPer100g = 6.0,
            servingOptions = listOf(
                ServingOption("1 Ball / Serving (60g)", "১ পরিবেশন / গোলা (৬০ গ্রাম)", 60, 80, isDefault = true),
                ServingOption("2 Servings (120g)", "২ পরিবেশন (১২০ গ্রাম)", 120, 160)
            )
        ),
        FoodItem(
            id = "begun_bhaja",
            nameEn = "Begun Bhaja / Baingan Fry (Fried Eggplant)",
            nameBn = "বেগুন ভাজা",
            category = FoodCategory.VEGETABLES,
            defaultCaloriesPer100g = 160,
            emoji = "🍆",
            descriptionEn = "Eggplant rounds seasoned with spices and pan-fried",
            descriptionBn = "হলুদ-মরিচ মেখে তেলে ভাজা বেগুন",
            carbsGramsPer100g = 9.0,
            proteinGramsPer100g = 1.8,
            fatGramsPer100g = 12.5,
            servingOptions = listOf(
                ServingOption("2 Slices (90g)", "২টি টুকরা (৯০ গ্রাম)", 90, 145, isDefault = true),
                ServingOption("1 Large Slice (50g)", "১টি বড় টুকরা (৫০ গ্রাম)", 50, 80)
            )
        ),
        FoodItem(
            id = "begun_bhorta",
            nameEn = "Begun Bhorta (Fire-Roasted Eggplant Mash)",
            nameBn = "বেগুন ভর্তা",
            category = FoodCategory.VEGETABLES,
            defaultCaloriesPer100g = 85,
            emoji = "🍆",
            descriptionEn = "Smoky charred aubergine mashed with mustard oil and spices",
            descriptionBn = "পোড়া বেগুনের সুস্বাদু ভর্তা সরিষার তেল সহ",
            carbsGramsPer100g = 7.0,
            proteinGramsPer100g = 1.5,
            fatGramsPer100g = 5.5,
            servingOptions = listOf(
                ServingOption("1 Serving (80g)", "১ পরিবেশন (৮০ গ্রাম)", 80, 68, isDefault = true),
                ServingOption("Half Bowl (120g)", "হাফ বাটি (১২০ গ্রাম)", 120, 102)
            )
        ),
        FoodItem(
            id = "shak_bhaji_palong",
            nameEn = "Palong Shak / Lal Shak Bhaji (Sauteed Spinach / Red Greens)",
            nameBn = "পালং শাক / লাল শাক ভাজি",
            category = FoodCategory.VEGETABLES,
            defaultCaloriesPer100g = 75,
            emoji = "🥬",
            descriptionEn = "Leafy greens stir-fried lightly with garlic, green chilli and mustard oil",
            descriptionBn = "রসুন ও কাঁচা মরিচ সহযোগে ভাজা দেশি শাক",
            carbsGramsPer100g = 4.5,
            proteinGramsPer100g = 3.0,
            fatGramsPer100g = 4.0,
            servingOptions = listOf(
                ServingOption("1 Standard Bowl (100g)", "১ বাটি (১০০ গ্রাম)", 100, 75, isDefault = true),
                ServingOption("Large Bowl (180g)", "বড় বাটি (১৮০ গ্রাম)", 180, 135)
            )
        ),
        FoodItem(
            id = "mixed_vegetables_curry",
            nameEn = "Sobji Labra / Mixed Veg Curry",
            nameBn = "পাঁচমিশালি সবজি লাবড়া",
            category = FoodCategory.VEGETABLES,
            defaultCaloriesPer100g = 80,
            emoji = "🥗",
            descriptionEn = "Seasonal mixed vegetables cooked with mild spices and panch phoron",
            descriptionBn = "পাঁচফোড়ন দিয়ে রান্না পাঁচমিশালি সবজি",
            carbsGramsPer100g = 10.0,
            proteinGramsPer100g = 2.2,
            fatGramsPer100g = 3.5,
            servingOptions = listOf(
                ServingOption("1 Medium Bowl (150g)", "১ বাটি (১৫০ গ্রাম)", 150, 120, isDefault = true),
                ServingOption("Large Bowl (250g)", "১ বড় বাটি (২৫০ গ্রাম)", 250, 200)
            )
        ),

        // SNACKS & STREET FOOD
        FoodItem(
            id = "singara_bangla",
            nameEn = "Bangla Singara (Spiced Potato Samosa)",
            nameBn = "সিঙ্গারা",
            category = FoodCategory.SNACKS_STREET,
            defaultCaloriesPer100g = 310,
            emoji = "🥟",
            descriptionEn = "Crispy pastry filled with potato, cauliflower, and peanuts fried in oil",
            descriptionBn = "আলু, ফুলকপি ও চিনাবাদামের পুর ভরা গরম সিঙ্গারা",
            carbsGramsPer100g = 34.0,
            proteinGramsPer100g = 4.5,
            fatGramsPer100g = 17.5,
            servingOptions = listOf(
                ServingOption("1 Medium Singara (55g)", "১টি সিঙ্গারা (৫৫ গ্রাম)", 55, 165, isDefault = true),
                ServingOption("2 Singaras (110g)", "২টি সিঙ্গারা (১১০ গ্রাম)", 110, 330),
                ServingOption("3 Singaras (165g)", "৩টি সিঙ্গারা (১৬৫ গ্রাম)", 165, 495)
            )
        ),
        FoodItem(
            id = "samusa_beef_chicken",
            nameEn = "Somosa (Meat or Veg Samosa)",
            nameBn = "সমুচা (চিকেন বা বিফ)",
            category = FoodCategory.SNACKS_STREET,
            defaultCaloriesPer100g = 320,
            emoji = "🥟",
            descriptionEn = "Thin crunchy triangular pastry stuffed with spiced minced meat or onions",
            descriptionBn = "মুচমুচে পাতলা খোলসে মাংস বা পেঁয়াজের পুর দেওয়া সমুচা",
            carbsGramsPer100g = 30.0,
            proteinGramsPer100g = 9.0,
            fatGramsPer100g = 18.0,
            servingOptions = listOf(
                ServingOption("1 Samosa (45g)", "১টি সমুচা (৪৫ গ্রাম)", 45, 140, isDefault = true),
                ServingOption("2 Samosas (90g)", "২টি সমুচা (৯০ গ্রাম)", 90, 280),
                ServingOption("3 Samosas (135g)", "৩টি সমুচা (১৩৫ গ্রাম)", 135, 420)
            )
        ),
        FoodItem(
            id = "fuchka_pani_puri",
            nameEn = "Fuchka with Tok (Pani Puri with Tamarind Water)",
            nameBn = "টকসহ ফুচকা",
            category = FoodCategory.SNACKS_STREET,
            defaultCaloriesPer100g = 190,
            emoji = "🧆",
            descriptionEn = "Crispy spheres stuffed with spiced potato-chickpeas and sour tamarind sauce",
            descriptionBn = "আলু, মটর ও তেঁতুলের টকে ভরা প্রিয় ফুচকা",
            carbsGramsPer100g = 30.0,
            proteinGramsPer100g = 4.0,
            fatGramsPer100g = 6.0,
            servingOptions = listOf(
                ServingOption("1 Plate (6 Pieces, 160g)", "১ প্লেট (৬টি, ১৬০ গ্রাম)", 160, 260, isDefault = true),
                ServingOption("1 Plate (10 Pieces, 250g)", "১ প্লেট (১০টি, ২৫০ গ্রাম)", 250, 420),
                ServingOption("3 Pieces (80g)", "৩টি ফুচকা (৮০ গ্রাম)", 80, 130)
            )
        ),
        FoodItem(
            id = "chotpoti_dhaka",
            nameEn = "Dhaka Chotpoti with Egg & Tok",
            nameBn = "ডিম ও টকসহ চটপটি",
            category = FoodCategory.SNACKS_STREET,
            defaultCaloriesPer100g = 145,
            emoji = "🍲",
            descriptionEn = "Spicy white pea stew topped with shredded boiled egg, cucumber, and chili",
            descriptionBn = "সিদ্ধ ডিম, কুচানো পেঁয়াজ ও তেঁতুলের চটপটি",
            carbsGramsPer100g = 22.0,
            proteinGramsPer100g = 6.5,
            fatGramsPer100g = 3.5,
            servingOptions = listOf(
                ServingOption("1 Standard Bowl (220g)", "১ বাটি চটপটি (২২০ গ্রাম)", 220, 310, isDefault = true),
                ServingOption("Half Bowl (110g)", "হাফ বাটি (১১০ গ্রাম)", 110, 155),
                ServingOption("Special Rich Plate (300g)", "স্পেশাল ডাবল ডিম প্লেট (৩০০ গ্রাম)", 300, 420)
            )
        ),
        FoodItem(
            id = "jhal_muri_bangla",
            nameEn = "Jhal Muri (Spicy Puffed Rice Street Mix)",
            nameBn = "ঝাল মুড়ি",
            category = FoodCategory.SNACKS_STREET,
            defaultCaloriesPer100g = 280,
            emoji = "🍿",
            descriptionEn = "Puffed rice tossed with mustard oil, chana, onions, green chili and chanachur",
            descriptionBn = "সরিষার তেল, চানাচুর ও মশলায় মাখা ঝাল মুড়ি",
            carbsGramsPer100g = 45.0,
            proteinGramsPer100g = 6.0,
            fatGramsPer100g = 9.0,
            servingOptions = listOf(
                ServingOption("1 Thonga / Paper Cone (70g)", "১ ঠোঙা (৭০ গ্রাম)", 70, 195, isDefault = true),
                ServingOption("Small Snack (40g)", "ছোট ঠোঙা (৪০ গ্রাম)", 40, 110),
                ServingOption("Large Bag (120g)", "বড় বাটি / ঠোঙা (১২০ গ্রাম)", 120, 335)
            )
        ),
        FoodItem(
            id = "piaju_pakora",
            nameEn = "Piaju / Onion Lentil Fritters",
            nameBn = "পিয়াজু",
            category = FoodCategory.SNACKS_STREET,
            defaultCaloriesPer100g = 310,
            emoji = "🧆",
            descriptionEn = "Crispy fried dal paste mixed with chopped onions and green chillies",
            descriptionBn = "মসুর ডাল ও পেঁয়াজ দিয়ে কড়া করে ভাজা মুচমুচে পিয়াজু",
            carbsGramsPer100g = 32.0,
            proteinGramsPer100g = 9.0,
            fatGramsPer100g = 16.0,
            servingOptions = listOf(
                ServingOption("2 Pieces (50g)", "২টি পিয়াজু (৫০ গ্রাম)", 50, 155, isDefault = true),
                ServingOption("4 Pieces (100g)", "৪টি পিয়াজু (১০০ গ্রাম)", 100, 310),
                ServingOption("1 Piece (25g)", "১টি পিয়াজু (২৫ গ্রাম)", 25, 78)
            )
        ),
        FoodItem(
            id = "beguni_fritter",
            nameEn = "Beguni (Eggplant Tempura/Fritter)",
            nameBn = "বেগুনি",
            category = FoodCategory.SNACKS_STREET,
            defaultCaloriesPer100g = 320,
            emoji = "🍆",
            descriptionEn = "Sliced eggplant coated in spiced gram flour batter and deep-fried",
            descriptionBn = "বেসনে চুবিয়ে ডুবো তেলে ভাজা মচমচে বেগুনি",
            carbsGramsPer100g = 28.0,
            proteinGramsPer100g = 5.5,
            fatGramsPer100g = 20.0,
            servingOptions = listOf(
                ServingOption("1 Piece (40g)", "১টি বেগুনি (৪০ গ্রাম)", 40, 130, isDefault = true),
                ServingOption("2 Pieces (80g)", "২টি বেগুনি (৮০ গ্রাম)", 80, 260)
            )
        ),

        // SWEETS & DESSERTS
        FoodItem(
            id = "roshogolla_sweet",
            nameEn = "Roshogolla / Rasgulla",
            nameBn = "রসগোল্লা",
            category = FoodCategory.SWEETS_DESSERT,
            defaultCaloriesPer100g = 220,
            emoji = "🍮",
            descriptionEn = "Spongy chhana (cottage cheese) balls cooked in light sugar syrup",
            descriptionBn = "ছানার তুলতুলে রসের মিষ্টি রসগোল্লা",
            carbsGramsPer100g = 48.0,
            proteinGramsPer100g = 4.0,
            fatGramsPer100g = 1.5,
            servingOptions = listOf(
                ServingOption("1 Medium Piece (50g)", "১টি রসগোল্লা (৫০ গ্রাম)", 50, 125, isDefault = true),
                ServingOption("2 Pieces (100g)", "২টি রসগোল্লা (১০০ গ্রাম)", 100, 250),
                ServingOption("3 Pieces (150g)", "৩টি রসগোল্লা (১৫০ গ্রাম)", 150, 375)
            )
        ),
        FoodItem(
            id = "gulab_jamun_gulapjam",
            nameEn = "Gulab Jamun / Kalo Jam",
            nameBn = "গোলাপ জাম / কালোজাম",
            category = FoodCategory.SWEETS_DESSERT,
            defaultCaloriesPer100g = 320,
            emoji = "🟤",
            descriptionEn = "Deep-fried mawa and flour balls soaked in saffron sugar syrup",
            descriptionBn = "ঘিয়ে ভাজা শিরায় ডোবানো সুস্বাদু কালোজাম",
            carbsGramsPer100g = 52.0,
            proteinGramsPer100g = 4.5,
            fatGramsPer100g = 10.5,
            servingOptions = listOf(
                ServingOption("1 Piece (50g)", "১টি গোলাপ জাম (৫০ গ্রাম)", 50, 175, isDefault = true),
                ServingOption("2 Pieces (100g)", "২টি গোলাপ জাম (১০০ গ্রাম)", 100, 350)
            )
        ),
        FoodItem(
            id = "mishti_doi_bogura",
            nameEn = "Bogra Mishti Doi (Sweet Yogurt)",
            nameBn = "বগুড়ার মিষ্টি দই",
            category = FoodCategory.SWEETS_DESSERT,
            defaultCaloriesPer100g = 150,
            emoji = "🏺",
            descriptionEn = "Caramelized fermented sweet whole milk curd in clay pot",
            descriptionBn = "মাটির পাত্রে জমানো খাঁটি মিষ্টি দই",
            carbsGramsPer100g = 22.0,
            proteinGramsPer100g = 4.0,
            fatGramsPer100g = 5.0,
            servingOptions = listOf(
                ServingOption("1 Small Clay Cup (100g)", "১ ছোট ভাঁড় (১০০ গ্রাম)", 100, 150, isDefault = true),
                ServingOption("1 Medium Bowl (150g)", "১ বাটি (১৫০ গ্রাম)", 150, 225),
                ServingOption("Large Serving (250g)", "বড় পরিবেশন (২৫০ গ্রাম)", 250, 375)
            )
        ),
        FoodItem(
            id = "payesh_kheer",
            nameEn = "Dudh Payesh / Kheer (Rice Pudding)",
            nameBn = "দুধ পায়েস / ক্ষীর",
            category = FoodCategory.SWEETS_DESSERT,
            defaultCaloriesPer100g = 160,
            emoji = "🥣",
            descriptionEn = "Sweet aromatic rice cooked in condensed milk with cardamom and nuts",
            descriptionBn = "ঘন দুধে সুগন্ধি চাল ও বাদাম দিয়ে রান্না পায়েস",
            carbsGramsPer100g = 24.0,
            proteinGramsPer100g = 4.5,
            fatGramsPer100g = 5.0,
            servingOptions = listOf(
                ServingOption("1 Small Bowl (120g)", "১ বাটি পায়েস (১২০ গ্রাম)", 120, 190, isDefault = true),
                ServingOption("Large Bowl (200g)", "১ বড় বাটি (২০০ গ্রাম)", 200, 320)
            )
        ),
        FoodItem(
            id = "jilapi_jalebi",
            nameEn = "Jilapi / Jalebi (Spiraled Syrup Sweet)",
            nameBn = "মুচমুচে জিলাপি",
            category = FoodCategory.SWEETS_DESSERT,
            defaultCaloriesPer100g = 350,
            emoji = "🥨",
            descriptionEn = "Fermented flour spirals fried crisp and soaked in sugar syrup",
            descriptionBn = "শিরায় ভেজানো রসালো গরম জিলাপি",
            carbsGramsPer100g = 68.0,
            proteinGramsPer100g = 2.0,
            fatGramsPer100g = 8.5,
            servingOptions = listOf(
                ServingOption("1 Medium Jilapi (45g)", "১টি জিলাপি (৪৫ গ্রাম)", 45, 150, isDefault = true),
                ServingOption("2 Jilapis (90g)", "২টি জিলাপি (৯০ গ্রাম)", 90, 300),
                ServingOption("Shahi Jilapi Piece (120g)", "১টি শাহী জিলাপি (১২০ গ্রাম)", 120, 420)
            )
        ),

        // FRESH FRUITS
        FoodItem(
            id = "mango_aam",
            nameEn = "Ripe Mango (Paka Aam)",
            nameBn = "পাকা আম (হিমসাগর / ল্যাংড়া)",
            category = FoodCategory.FRUITS,
            defaultCaloriesPer100g = 60,
            emoji = "🥭",
            descriptionEn = "Sweet juicy seasonal king of fruits",
            descriptionBn = "মিষ্টি ও রসালো দেশি পাকা আম",
            carbsGramsPer100g = 15.0,
            proteinGramsPer100g = 0.8,
            fatGramsPer100g = 0.4,
            servingOptions = listOf(
                ServingOption("1 Medium Mango (200g flesh)", "১টি মাঝারি পাকা আম (২০০ গ্রাম)", 200, 120, isDefault = true),
                ServingOption("1 Cup Slices (150g)", "১ কাপ কাটা টুকরো (১৫০ গ্রাম)", 150, 90),
                ServingOption("1 Small Mango (120g flesh)", "১টি ছোট আম (১২০ গ্রাম)", 120, 72)
            )
        ),
        FoodItem(
            id = "banana_kola",
            nameEn = "Banana (Sagor / Champa Kola)",
            nameBn = "কলা (সাগর / চম্পা)",
            category = FoodCategory.FRUITS,
            defaultCaloriesPer100g = 89,
            emoji = "🍌",
            descriptionEn = "Energy-packed potassium rich fresh banana",
            descriptionBn = "পুষ্টিকর ও শক্তিবর্ধক তাজা কলা",
            carbsGramsPer100g = 23.0,
            proteinGramsPer100g = 1.1,
            fatGramsPer100g = 0.3,
            servingOptions = listOf(
                ServingOption("1 Medium Banana (118g)", "১টি মাঝারি কলা (১১৮ গ্রাম)", 118, 105, isDefault = true),
                ServingOption("1 Small Banana (80g)", "১টি ছোট কলা (৮০ গ্রাম)", 80, 72),
                ServingOption("2 Medium Bananas (236g)", "২টি মাঝারি কলা (২৩৬ গ্রাম)", 236, 210)
            )
        ),
        FoodItem(
            id = "apple_fresh",
            nameEn = "Fresh Apple (Seb)",
            nameBn = "তাজা আপেল",
            category = FoodCategory.FRUITS,
            defaultCaloriesPer100g = 52,
            emoji = "🍎",
            descriptionEn = "Crisp, sweet, fiber-rich whole apple",
            descriptionBn = "আঁশযুক্ত ও স্বাস্থ্যকর তাজা আপেল",
            carbsGramsPer100g = 14.0,
            proteinGramsPer100g = 0.3,
            fatGramsPer100g = 0.2,
            servingOptions = listOf(
                ServingOption("1 Medium Apple (180g)", "১টি মাঝারি আপেল (১৮০ গ্রাম)", 180, 95, isDefault = true),
                ServingOption("1 Small Apple (130g)", "১টি ছোট আপেল (১৩০ গ্রাম)", 130, 68),
                ServingOption("1 Large Apple (220g)", "১টি বড় আপেল (২২০ গ্রাম)", 220, 115)
            )
        ),
        FoodItem(
            id = "papaya_paka_pepe",
            nameEn = "Ripe Papaya (Paka Pepe)",
            nameBn = "পাকা পেঁপে",
            category = FoodCategory.FRUITS,
            defaultCaloriesPer100g = 43,
            emoji = "🍈",
            descriptionEn = "Digestive enzymes rich sweet tropical fruit",
            descriptionBn = "হজমকারক ভিটামিন সমৃদ্ধ পাকা পেঁপে",
            carbsGramsPer100g = 11.0,
            proteinGramsPer100g = 0.5,
            fatGramsPer100g = 0.3,
            servingOptions = listOf(
                ServingOption("1 Cup Cubes (145g)", "১ কাপ কাটা পেঁপে (১৪৫ গ্রাম)", 145, 62, isDefault = true),
                ServingOption("1 Large Slice (200g)", "১ বড় ফালি (২০০ গ্রাম)", 200, 86)
            )
        ),
        FoodItem(
            id = "guava_peyara",
            nameEn = "Guava (Deshi Peyara)",
            nameBn = "দেশি পেয়ারা",
            category = FoodCategory.FRUITS,
            defaultCaloriesPer100g = 68,
            emoji = "🍐",
            descriptionEn = "Vitamin C packed crunchy green guava",
            descriptionBn = "ভিটামিন সি সমৃদ্ধ কচি তাজা পেয়ারা",
            carbsGramsPer100g = 14.0,
            proteinGramsPer100g = 2.6,
            fatGramsPer100g = 1.0,
            servingOptions = listOf(
                ServingOption("1 Medium Guava (150g)", "১টি মাঝারি পেয়ারা (১৫০ গ্রাম)", 150, 100, isDefault = true),
                ServingOption("1 Small Guava (90g)", "১টি ছোট পেয়ারা (৯০ গ্রাম)", 90, 60)
            )
        ),
        FoodItem(
            id = "watermelon_tormuj",
            nameEn = "Watermelon (Tormuj)",
            nameBn = "তরমুজ",
            category = FoodCategory.FRUITS,
            defaultCaloriesPer100g = 30,
            emoji = "🍉",
            descriptionEn = "Hydrating sweet summer melon slices",
            descriptionBn = "পানিপূর্ণ ও শীতল মিষ্টি তরমুজ",
            carbsGramsPer100g = 7.5,
            proteinGramsPer100g = 0.6,
            fatGramsPer100g = 0.2,
            servingOptions = listOf(
                ServingOption("1 Wedge / Slice (280g)", "১ ফালি তরমুজ (২৮০ গ্রাম)", 280, 85, isDefault = true),
                ServingOption("1 Cup Diced (150g)", "১ বাটি কাটা তরমুজ (১৫০ গ্রাম)", 150, 46)
            )
        ),

        // DRINKS & BEVERAGES
        FoodItem(
            id = "cha_dudh_milk_tea",
            nameEn = "Dudh Cha / Sweet Milk Tea",
            nameBn = "দুধ চা (চিনিসহ)",
            category = FoodCategory.DRINKS,
            defaultCaloriesPer100g = 70,
            emoji = "☕",
            descriptionEn = "Everyday street-side strong black tea brewed with whole condensed milk and sugar",
            descriptionBn = "ঘন দুধ ও চিনি দিয়ে জ্বাল দেওয়া গরম চা",
            carbsGramsPer100g = 10.0,
            proteinGramsPer100g = 2.2,
            fatGramsPer100g = 2.4,
            servingOptions = listOf(
                ServingOption("1 Regular Cup (120ml)", "১ কাপ দুধ চা (১২০ মিলি)", 120, 85, isDefault = true),
                ServingOption("1 Large Mug (200ml)", "১ মাগ চা (২০০ মিলি)", 200, 140),
                ServingOption("1 Small Cutting Cup (60ml)", "১ কাটিং চা (৬০ মিলি)", 60, 45)
            )
        ),
        FoodItem(
            id = "cha_rong_black_tea",
            nameEn = "Rong Cha / Black Tea (No Sugar)",
            nameBn = "রং চা / লাল চা (চিনি ছাড়া)",
            category = FoodCategory.DRINKS,
            defaultCaloriesPer100g = 2,
            emoji = "🍵",
            descriptionEn = "Plain black tea with ginger, clove or lemon and zero sugar",
            descriptionBn = "আদা, লবঙ্গ ও লেবু দেওয়া চিনি ছাড়া লাল চা",
            carbsGramsPer100g = 0.4,
            proteinGramsPer100g = 0.1,
            fatGramsPer100g = 0.0,
            servingOptions = listOf(
                ServingOption("1 Cup (150ml, No Sugar)", "১ কাপ লাল চা (১৫০ মিলি)", 150, 3, isDefault = true),
                ServingOption("1 Cup with 1 Tsp Sugar (150ml)", "১ চামচ চিনিসহ ১ কাপ (১৫০ মিলি)", 150, 20)
            )
        ),
        FoodItem(
            id = "lassi_sweet",
            nameEn = "Sweet Lassi (Curd Beverage)",
            nameBn = "মিষ্টি লাচ্ছি",
            category = FoodCategory.DRINKS,
            defaultCaloriesPer100g = 80,
            emoji = "🥛",
            descriptionEn = "Chilled blended yogurt drink with sugar, milk, and rose water",
            descriptionBn = "মিষ্টি দই ব্লেন্ড করা ঠান্ডা বরফ লাচ্ছি",
            carbsGramsPer100g = 13.0,
            proteinGramsPer100g = 3.0,
            fatGramsPer100g = 2.0,
            servingOptions = listOf(
                ServingOption("1 Glass (250ml)", "১ গ্লাস লাচ্ছি (২৫০ মিলি)", 250, 200, isDefault = true),
                ServingOption("1 Large Glass (350ml)", "১ বড় গ্লাস (৩৫০ মিলি)", 350, 280)
            )
        ),
        FoodItem(
            id = "borhani_traditional",
            nameEn = "Old Dhaka Borhani (Spiced Yogurt Drink)",
            nameBn = "পুরান ঢাকার বোরহানি",
            category = FoodCategory.DRINKS,
            defaultCaloriesPer100g = 65,
            emoji = "🥛",
            descriptionEn = "Festive yogurt beverage seasoned with mint, mustard, bit lobon, and spices",
            descriptionBn = "বিয়ে ও দাওয়াতের ঐতিহ্যবাহী হজমকারক বোরহানি",
            carbsGramsPer100g = 6.0,
            proteinGramsPer100g = 3.0,
            fatGramsPer100g = 3.2,
            servingOptions = listOf(
                ServingOption("1 Glass (200ml)", "১ গ্লাস বোরহানি (২০০ মিলি)", 200, 130, isDefault = true),
                ServingOption("1 Large Glass (300ml)", "১ বড় গ্লাস (৩০০ মিলি)", 300, 195)
            )
        ),
        FoodItem(
            id = "dab_coconut_water",
            nameEn = "Green Coconut Water (Daber Pani)",
            nameBn = "ডাবের পানি",
            category = FoodCategory.DRINKS,
            defaultCaloriesPer100g = 19,
            emoji = "🥥",
            descriptionEn = "Natural electrolyte fresh coconut water",
            descriptionBn = "তাজা কচি ডাবের প্রাকৃতিক পানি",
            carbsGramsPer100g = 3.7,
            proteinGramsPer100g = 0.7,
            fatGramsPer100g = 0.2,
            servingOptions = listOf(
                ServingOption("1 Whole Green Coconut (250ml)", "১টি আস্ত ডাবের পানি (২৫০ মিলি)", 250, 48, isDefault = true),
                ServingOption("1 Large Coconut (350ml)", "১টি বড় ডাব (৩৫০ মিলি)", 350, 68)
            )
        ),

        // GLOBAL & POPULAR FAST FOOD
        FoodItem(
            id = "pizza_margherita",
            nameEn = "Cheese Pizza (Margherita)",
            nameBn = "চিজ পিৎজা (মার্গারিটা)",
            category = FoodCategory.GLOBAL_FAST_FOOD,
            defaultCaloriesPer100g = 265,
            emoji = "🍕",
            descriptionEn = "Crust topped with marinara sauce and baked mozzarella cheese",
            descriptionBn = "টমেটো সস ও গলানো মোজারেলা চিজ পিৎজা",
            carbsGramsPer100g = 32.0,
            proteinGramsPer100g = 11.5,
            fatGramsPer100g = 10.0,
            servingOptions = listOf(
                ServingOption("1 Regular Slice (105g)", "১ টুকরো পিৎজা (১০৫ গ্রাম)", 105, 275, isDefault = true),
                ServingOption("2 Slices (210g)", "২ টুকরো পিৎজা (২১০ গ্রাম)", 210, 550),
                ServingOption("Whole Personal Pizza (300g)", "১টি পার্সোনাল পিৎজা (৩০০ গ্রাম)", 300, 790)
            )
        ),
        FoodItem(
            id = "burger_beef_chicken",
            nameEn = "Chicken or Beef Burger",
            nameBn = "চিকেন বা বিফ বার্গার",
            category = FoodCategory.GLOBAL_FAST_FOOD,
            defaultCaloriesPer100g = 250,
            emoji = "🍔",
            descriptionEn = "Bun filled with meat patty, mayonnaise, lettuce, and sauces",
            descriptionBn = "মাংসের প্যাটি, মেয়োনিজ ও সসের বার্গার",
            carbsGramsPer100g = 26.0,
            proteinGramsPer100g = 13.0,
            fatGramsPer100g = 11.0,
            servingOptions = listOf(
                ServingOption("1 Regular Burger (160g)", "১টি সাধারণ বার্গার (১৬০ গ্রাম)", 160, 400, isDefault = true),
                ServingOption("1 Cheese Burger (180g)", "১টি চিজ বার্গার (১৮০ গ্রাম)", 180, 480),
                ServingOption("1 Double Patty Burger (240g)", "১টি ডাবল প্যাটি বার্গার (২৪০ গ্রাম)", 240, 650)
            )
        ),
        FoodItem(
            id = "french_fries",
            nameEn = "French Fries (Fried Potatoes)",
            nameBn = "ফ্রেঞ্চ ফ্রাই (আলু ভাজি)",
            category = FoodCategory.GLOBAL_FAST_FOOD,
            defaultCaloriesPer100g = 312,
            emoji = "🍟",
            descriptionEn = "Crispy salted deep fried potato batons",
            descriptionBn = "লবণাক্ত ও মুচমুচে ডুবো তেলে ভাজা আলু",
            carbsGramsPer100g = 41.0,
            proteinGramsPer100g = 3.4,
            fatGramsPer100g = 15.0,
            servingOptions = listOf(
                ServingOption("Medium Order (115g)", "মাঝারি প্যাকেট (১১৫ গ্রাম)", 115, 360, isDefault = true),
                ServingOption("Small Order (70g)", "ছোট প্যাকেট (৭০ গ্রাম)", 70, 220),
                ServingOption("Large Order (160g)", "বড় প্যাকেট (১৬০ গ্রাম)", 160, 500)
            )
        ),
        FoodItem(
            id = "fried_chicken_crispy",
            nameEn = "Crispy Fried Chicken (Broast)",
            nameBn = "ক্রিস্পি ফ্রাইড চিকেন",
            category = FoodCategory.GLOBAL_FAST_FOOD,
            defaultCaloriesPer100g = 260,
            emoji = "🍗",
            descriptionEn = "Flour breaded crunchy deep fried seasoned chicken pieces",
            descriptionBn = "মচমচে তেলে ভাজা ব্রোস্ট চিকেন",
            carbsGramsPer100g = 11.0,
            proteinGramsPer100g = 19.0,
            fatGramsPer100g = 16.0,
            servingOptions = listOf(
                ServingOption("1 Drumstick / Piece (110g)", "১ পিস লেগ / টুকরা (১১০ গ্রাম)", 110, 285, isDefault = true),
                ServingOption("2 Pieces (220g)", "২ পিস ফ্রাইড চিকেন (২২০ গ্রাম)", 220, 570),
                ServingOption("1 Chicken Breast Piece (160g)", "১ বড় ব্রেস্ট পিস (১৬০ গ্রাম)", 160, 410)
            )
        ),
        FoodItem(
            id = "shawarma_roll",
            nameEn = "Chicken Shawarma / Kathi Roll",
            nameBn = "চিকেন শাওয়ারমা / কাঠি রোল",
            category = FoodCategory.GLOBAL_FAST_FOOD,
            defaultCaloriesPer100g = 210,
            emoji = "🌯",
            descriptionEn = "Pita or paratha flatbread rolled with grilled chicken strips, garlic sauce, and salad",
            descriptionBn = "রুটিতে মোড়ানো গ্রিল চিকেন ও মেয়নিজ সস রোল",
            carbsGramsPer100g = 22.0,
            proteinGramsPer100g = 11.0,
            fatGramsPer100g = 8.5,
            servingOptions = listOf(
                ServingOption("1 Standard Roll (180g)", "১টি শাওয়ারমা রোল (১৮০ গ্রাম)", 180, 380, isDefault = true),
                ServingOption("1 Jumbo Roll (250g)", "১টি জাম্বো রোল (২৫০ গ্রাম)", 250, 520)
            )
        ),
        FoodItem(
            id = "noodles_chowmein",
            nameEn = "Egg & Veg Noodles / Chowmein",
            nameBn = "ডিম ও সবজি চাউমিন / নুডুলস",
            category = FoodCategory.GLOBAL_FAST_FOOD,
            defaultCaloriesPer100g = 165,
            emoji = "🍜",
            descriptionEn = "Wok-tossed noodles with shredded cabbage, carrots, egg, and soy sauce",
            descriptionBn = "ডিম ও সবজি দিয়ে ভাজা সুস্বাদু চাউমিন নুডুলস",
            carbsGramsPer100g = 24.0,
            proteinGramsPer100g = 5.0,
            fatGramsPer100g = 5.5,
            servingOptions = listOf(
                ServingOption("1 Plate (250g)", "১ প্লেট (২৫০ গ্রাম)", 250, 410, isDefault = true),
                ServingOption("1 Cup / Bowl (140g)", "১ বাটি (১৪০ গ্রাম)", 140, 230),
                ServingOption("Half Plate (125g)", "হাফ প্লেট (১২৫ গ্রাম)", 125, 205)
            )
        )
    )
}
