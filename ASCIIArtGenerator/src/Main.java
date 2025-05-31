import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Map<Character, String[]> font = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Voer een tekst in: ");
        String input = scanner.nextLine().toUpperCase();

        for (char c2 = 'a'; c2 <= 'z'; c2++) {
            font.put(c2, font.get(Character.toUpperCase(c2)));
        }

        for (int row = 0; row < 5; row++) {
            for (char c : input.toCharArray()) {
                String[] letter = font.getOrDefault(c, new String[]{
                        "     ", "     ", "     ", "     ", "     "});
                System.out.print(letter[row] + "  ");
            }
            System.out.println();
        }
    }

    static {
        font.put('A', new String[]{
                "  A  ",
                " A A ",
                "AAAAA",
                "A   A",
                "A   A"
        });
        font.put('B', new String[]{
                "BBBB ",
                "B   B",
                "BBBB ",
                "B   B",
                "BBBB "
        });
        font.put('C', new String[]{
                " CCC ",
                "C   C",
                "C    ",
                "C   C",
                " CCC "
        });
        font.put('D', new String[]{
                "DDD  ",
                "D  D ",
                "D   D",
                "D  D ",
                "DDD  "
        });
        font.put('E', new String[]{
                "EEEEE",
                "E    ",
                "EEE  ",
                "E    ",
                "EEEEE"
        });
        font.put('F', new String[]{
                "FFFFF",
                "F    ",
                "FFF  ",
                "F    ",
                "F    "
        });
        font.put('G', new String[]{
                " GGG ",
                "G    ",
                "G GGG",
                "G   G",
                " GGG "
        });
        font.put('H', new String[]{
                "H   H",
                "H   H",
                "HHHHH",
                "H   H",
                "H   H"
        });
        font.put('I', new String[]{
                "IIIII",
                "  I  ",
                "  I  ",
                "  I  ",
                "IIIII"
        });
        font.put('J', new String[]{
                "JJJJJ",
                "    J",
                "    J",
                "J   J",
                " JJJ "
        });
        font.put('K', new String[]{
                "K   K",
                "K  K ",
                "KKK  ",
                "K  K ",
                "K   K"
        });
        font.put('L', new String[]{
                "L    ",
                "L    ",
                "L    ",
                "L    ",
                "LLLLL"
        });
        font.put('M', new String[]{
                "M   M",
                "MM MM",
                "M M M",
                "M   M",
                "M   M"
        });
        font.put('N', new String[]{
                "N   N",
                "NN  N",
                "N N N",
                "N  NN",
                "N   N"
        });
        font.put('O', new String[]{
                " OOO ",
                "O   O",
                "O   O",
                "O   O",
                " OOO "
        });
        font.put('P', new String[]{
                "PPPP ",
                "P   P",
                "PPPP ",
                "P    ",
                "P    "
        });
        font.put('Q', new String[]{
                " QQQ ",
                "Q   Q",
                "Q Q Q",
                "Q  Q ",
                " QQ Q"
        });
        font.put('R', new String[]{
                "RRRR ",
                "R   R",
                "RRRR ",
                "R  R ",
                "R   R"
        });
        font.put('S', new String[]{
                " SSS ",
                "S    ",
                " SSS ",
                "    S",
                " SSS "
        });
        font.put('T', new String[]{
                "TTTTT",
                "  T  ",
                "  T  ",
                "  T  ",
                "  T  "
        });
        font.put('U', new String[]{
                "U   U",
                "U   U",
                "U   U",
                "U   U",
                " UUU "
        });
        font.put('V', new String[]{
                "V   V",
                "V   V",
                "V   V",
                " V V ",
                "  V  "
        });
        font.put('W', new String[]{
                "W   W",
                "W   W",
                "W W W",
                "WW WW",
                "W   W"
        });
        font.put('X', new String[]{
                "X   X",
                " X X ",
                "  X  ",
                " X X ",
                "X   X"
        });
        font.put('Y', new String[]{
                "Y   Y",
                " Y Y ",
                "  Y  ",
                "  Y  ",
                "  Y  "
        });
        font.put('Z', new String[]{
                "ZZZZZ",
                "   Z ",
                "  Z  ",
                " Z   ",
                "ZZZZZ"
        });
        font.put(' ', new String[]{
                "     ",
                "     ",
                "     ",
                "     ",
                "     "
        });
        font.put('0', new String[]{
                " 000 ",
                "0   0",
                "0   0",
                "0   0",
                " 000 "
        });
        font.put('1', new String[]{
                "  1  ",
                " 11  ",
                "  1  ",
                "  1  ",
                "11111"
        });
        font.put('2', new String[]{
                " 222 ",
                "2   2",
                "   2 ",
                "  2  ",
                "22222"
        });
        font.put('3', new String[]{
                "33333",
                "    3",
                " 333 ",
                "    3",
                "33333"
        });
        font.put('4', new String[]{
                "4  4 ",
                "4  4 ",
                "44444",
                "   4 ",
                "   4 "
        });
        font.put('5', new String[]{
                "55555",
                "5    ",
                "5555 ",
                "    5",
                "5555 "
        });
        font.put('6', new String[]{
                " 666 ",
                "6    ",
                "6666 ",
                "6   6",
                " 666 "
        });
        font.put('7', new String[]{
                "77777",
                "   7 ",
                "  7  ",
                " 7   ",
                "7    "
        });
        font.put('8', new String[]{
                " 888 ",
                "8   8",
                " 888 ",
                "8   8",
                " 888 "
        });
        font.put('9', new String[]{
                " 999 ",
                "9   9",
                " 9999",
                "    9",
                " 999 "
        });
        font.put('!', new String[]{
                "  !  ",
                "  !  ",
                "  !  ",
                "     ",
                "  !  "
        });
        font.put('?', new String[]{
                "???  ",
                "   ? ",
                "  ?? ",
                "     ",
                "  ?  "
        });
        font.put('.', new String[]{
                "     ",
                "     ",
                "     ",
                "     ",
                "  .  "
        });
        font.put(',', new String[]{
                "     ",
                "     ",
                "     ",
                "  ,  ",
                " ,   "
        });
        font.put(':', new String[]{
                "     ",
                "  :  ",
                "     ",
                "  :  ",
                "     "
        });
        font.put('\'', new String[]{
                "  '  ",
                "  '  ",
                "     ",
                "     ",
                "     "
        });
        font.put('"', new String[]{
                " \" \" ",
                " \" \" ",
                "     ",
                "     ",
                "     "
        });
        font.put('-', new String[]{
                "     ",
                "     ",
                " --- ",
                "     ",
                "     "
        });
        font.put('(', new String[]{
                "  (  ",
                " (   ",
                " (   ",
                " (   ",
                "  (  "
        });
        font.put(')', new String[]{
                "  )  ",
                "   ) ",
                "   ) ",
                "   ) ",
                "  )  "
        });
        font.put('@', new String[]{
                " @@@ ",
                "@   @",
                "@ @@ ",
                "@    ",
                " @@@ "
        });
    }
}