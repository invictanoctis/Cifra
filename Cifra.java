public class Cifra {

    enum Mode {
        ENCRYPT, DECRYPT
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No command given... Please do Cifra help for more information.");
            return;
        }
        
        String command = args[0];

        switch (command.toLowerCase()) { // switch case since i want to add more encryption methods later
            case "help":
                String helpMessage = """
                    ==============================

                    Cifra - Command Line Cipher Tool
                    
                    ==============================

                    USAGE:
                    java Cifra <command> [arguments]

                    ==============================

                    COMMANDS:
                    caeser <encrypt|decrypt> <key> <text>
                        Encrypts or decrypts the given text using a Caesar cipher.
                        <encrypt|decrypt>  : Choose whether to encrypt or decrypt.
                        <key>              : Integer shift value for the cipher.
                        <text>             : The text to be encrypted or decrypted.

                    ==============================
                    """;

                System.out.println(helpMessage);
                return;
            
            case "caeser":
                try {
                    Mode mode;

                    switch (args[1].toLowerCase()) {
                        case "encrypt":
                            mode = Mode.ENCRYPT;
                            break;
                        case "decrypt":
                            mode = Mode.DECRYPT;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid mode...");
                    }

                    int key = Integer.parseInt(args[2]);
                    String targetString = args[3];

                    System.out.println(Cifra.caeser(mode, key, targetString));
                    
                    return;

                }
                catch (RuntimeException e){
                    System.out.println("Wrong command syntax... Please have a look at the help command!");
                    return;
                }
            

            default:
                System.out.println("Unknown command:" + command);
                System.out.println("Please have a look at the help command!");
        }
    }

    private static String caeser(Mode mode, int key, String target) {
        switch (mode){

            case ENCRYPT:
                StringBuilder result = new StringBuilder();
                for (char c : target.toCharArray()) {
                    if (Character.isLetter(c)) {
                        char base = Character.isUpperCase(c) ? 'A' : 'a';
                        result.append((char) ((c - base + key) % 26 + base)); // letter "D" and key 3 -> (68 - 65 + 3) % 26 + 65 = 71 -> "G"
                    } else {
                        result.append(c);
                    }
                }
                return result.toString();
            
            case DECRYPT:
                return caeser(Mode.ENCRYPT, 26 - (key % 26), target); // same as -key basically but recursion is always nice
            
            default:
                throw new IllegalArgumentException("Unknown mode: " + mode); // not reachable technically but compiler is complaining
        }
    }
}