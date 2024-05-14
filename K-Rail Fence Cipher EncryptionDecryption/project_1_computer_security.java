package project_1;

public class project_1_computer_security {

	static String encryption(String text, int key) {
		String encryptedText = "";
		
		boolean check = false;
		int j = 0;
		int row = key;
		int col = text.length();
		char[][] a = new char[row][col];
 		
		for(int i = 0; i < col; i++) {
			if(j == 0 || j == key -1) {
				check = !check;
			}
				a[j][i] = text.charAt(i);
				
				if(check) {
					j++;
				}else {
					j--;
				}
		}
		
		
		for(int i = 0; i < row; i++) {
			for(int k = 0; k < col; k++) {
				if(a[i][k] != 0) {
					encryptedText += a[i][k];
				}
			}
		}
				
		return encryptedText;
	}
    
    
    
    
    
    
    static char[][] printSegment(String plainText, int key) {
    	plainText.replaceAll("\\s", "");
        int row = key;
        int col = plainText.length();
        char[][] a = new char[row][col];

        boolean check = false;
        int j = 0;
        for (int i = 0; i < col; i++) {
            if (j == 0 || j == key - 1) {
                check = !check;
            }
            a[j][i] = plainText.charAt(i);

            if (check) {
                j++;
            } else {
                j--;
            }
        }

        return a;
    }
    

    
    
    
    
    static String decryption(String text, int key) {
        String decryptedText = "";

        // Initialize variables
        boolean check = false;
        int j = 0;
        int row = key;
        int col = text.length();
        char[][] railFence = new char[row][col];

        // Fill the rail fence in the same zigzag pattern as encryption
        for (int i = 0; i < col; i++) {
            if (j == 0 || j == key - 1) {
                check = !check;
            }

            railFence[j][i] = '$';

            if (check) {
                j++;
            } else {
                j--;
            }
        }

        // Fill the rail fence with the characters from the text
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                if (railFence[i][k] == '$' && index < text.length()) {
                    railFence[i][k] = text.charAt(index++);
                }
            }
        }

        // Read off the characters from the rail fence to obtain decrypted text
        j = 0;
        check = false;
        for (int i = 0; i < col; i++) {
            if (j == 0 || j == key - 1) {
                check = !check;
            }
            decryptedText += railFence[j][i];

            if (check) {
                j++;
            } else {
                j--;
            }
        }

        return decryptedText;
    }
}
