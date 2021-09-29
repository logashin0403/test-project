package service;

class CleanData {

    static StringBuilder cleanFromUnwantedSymbol(StringBuilder stringFromFile) {

        stringFromFile = new StringBuilder(regularClean(stringFromFile.toString()));
        stringFromFile = new StringBuilder(spaceClean(stringFromFile.toString()));

        return stringFromFile;
    }

    //чистка по регуляркам
    private static String regularClean(String str) {

        str = str.replaceAll("\\<.*?>", " ");
        str = str.replaceAll("&nbsp;|&mdash;|&rsquo;|&laquo;|&raquo;", "");
        str = str.replaceAll("[0-9]", "");
        str = str.replaceAll(" - ", "");
        str = str.replaceAll("[,:;.&!?)('\\]\\[]", "").trim();

        return str;
    }

    //удаление лишних пробелов
    private static String spaceClean(String str) {

        for (int i = 0; i < str.length(); i++) {
            if (String.valueOf(str.charAt(i)).equals(" ")) {
                if (i + 1 != str.length()) {
                    if (String.valueOf(str.charAt(i + 1)).equals(" ")) {
                        str = str.substring(0, i) + str.substring(i + 1);
                        i = 0;
                    }
                }
            }
        }

        return str;
    }
}
