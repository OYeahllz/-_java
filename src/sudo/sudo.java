package sudo;

import java.util.Arrays;

public class sudo {
	private static int[][] s =  { { 0, 0, 0, 0, 0, 0, 0, 0, 5 }, { 7,0, 2, 0, 0, 0, 1, 0, 9 }, { 4, 0, 0, 0,
		  8, 9, 0, 6, 0 }, { 0, 0, 0, 6, 5, 0, 9, 0, 0 }, { 2, 0, 0, 9, 0, 8, 0, 0, 7
		  }, { 0, 0, 9, 0, 4, 2, 0, 0, 0 }, { 0, 3, 0, 5, 7, 0, 0, 0, 8 }, { 8, 0, 4,
		  0, 0, 0, 3, 0, 6 }, { 9, 0, 0, 0, 0, 0, 0, 0, 0 } };

	/**
	  { { 9, 1, 2, 6, 3, 7, 4, 5, 8 }, { 0, 0, 3, 0, 0, 9, 0, 2, 1 }, { 0, 0, 0, 4,
	  0, 2, 0, 0, 0 }, { 0, 8, 0, 0, 4, 0, 9, 0, 2 }, { 0, 0, 0, 0, 7, 0, 0, 0, 0
	  }, { 5, 0, 4, 0, 6, 0, 0, 1, 0 }, { 0, 0, 0, 5, 0, 6, 0, 0, 0 }, { 2, 5, 0,
	  7, 0, 0, 8, 0, 0 }, { 0, 3, 0, 0, 0, 0, 0, 9, 5 } }
	  
	  
	  
	  
	  { { 8, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
			{ 0, 7, 0, 0, 9, 0, 2, 0, 0 }, { 0, 5, 0, 0, 0, 7, 0, 0, 0 }, { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 3, 0 }, { 0, 0, 1, 0, 0, 0, 0, 6, 8 }, { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
			{ 0, 9, 0, 0, 0, 0, 4, 0, 0 } };
	  
	  
	  
	  
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		int CountForNode = 0;
		// ��ȡ����
		int[][] sudoku = new int[9][9];
		sudoku = s;
		SudokuNode mysudo = new SudokuNode();
		// NodeOperation nodeOperation=new NodeOperation();
		// mysudo.head = null;
		int[] candidate = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		// ��������������Ϊ0����������Ҫ�����������ڵ㣻
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku[i][j] == 0) {
					mysudo.addNode(i, j, sudoku[i][j], candidate);
					CountForNode++;
				}
			}
		}
		// �����ڵ�
		NodeOperation(mysudo, CountForNode, sudoku);

//		//�����ѡ��ֻ��һ����ֱ�����
//		SudokuNode.Node temp = mysudo.head;
//		while(temp!=null) {
//			if(temp.candidate.length==1) {
//				sudoku[temp.row][temp.col]=temp.candidate[0];
//			}
//			temp=temp.next;
//		}		
		
		// �������
		// ���û��ݺ���
		SudokuNode.Node temp = mysudo.head;
		cotex(CountForNode, mysudo, sudoku, temp, startTime);
		// return;
	}

	// �������
	public static void printArray(int[][] sudoku, long time) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(sudoku[i][j] + " ");
			}
			System.out.println();
		}
		// System.out.println();
		System.out.print("��ʱ" + time + "us");
	}

	/**
	 * ���ݺ���
	 */
	public static void cotex(int CountForNode, SudokuNode mysudo, int[][] sudoku, SudokuNode.Node temp,
			long startTime) {
		if (temp == null) {
			long endTime = System.nanoTime();

			printArray(sudoku, (endTime - startTime)/1000);
			return;// ���
			// System.exit(0);
		} else {
			// �����裺���룬�ݹ飬�س�
			for (int i = 0; i < temp.candidate.length; i++) {
				// ÿ������temp.candidate.length��������
				// ����

				// �жϸú�ѡ���ɲ����У����о�����һ���������У��ͻس�
				if (isOK(temp.row, temp.col, temp.candidate[i], sudoku)) {
					sudoku[temp.row][temp.col] = temp.candidate[i];
					// ���У�������һ����

					cotex(CountForNode, mysudo, sudoku, temp.next, startTime);
					if (temp != null)
						sudoku[temp.row][temp.col] = 0;
				}
			}
		}

	}

	// ���򣬺�ѡ�����ٵĽڵ��ŵ�һ

	public static boolean isOK(int row, int col, int value, int[][] sudoku) {
		// �����У���
		for (int m = 0; m < 9; m++) {
			if (value == sudoku[row][m] || value == sudoku[m][col]) {
				return false;
			}
		}

		// ��������
		int tempRow = row / 3;
		int tempCol = col / 3;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (sudoku[tempRow * 3 + i][tempCol * 3 + j] == value) {
					return false;
				}
			}
		}
		return true;
	}

	public static void NodeOperation(SudokuNode mysudo, int CountForNode, int[][] sudoku) {
		// ������ѡ�� �У��У�����
		SudokuNode.Node temp = mysudo.head;
		int candidateLen = temp.candidate.length;

		for (int l = 0; l < CountForNode; l++) {

			candidateLen = temp.candidate.length;

			// �б����б���
			for (int i = 0; i < 9; i++) {
				if (sudoku[temp.row][i] != 0 || sudoku[i][temp.col] != 0) {
					for (int j = 0; j < candidateLen; j++) {
						if (temp.candidate[j] == sudoku[temp.row][i]) {
							temp.candidate[j] = temp.candidate[temp.candidate.length - 1];
							temp.candidate = Arrays.copyOf(temp.candidate, temp.candidate.length - 1);
							candidateLen--;
							continue;

							// ��������������ɾ����Ӧ��ѡ��
						}
						if (temp.candidate[j] == sudoku[i][temp.col]) {
							temp.candidate[j] = temp.candidate[temp.candidate.length - 1];
							temp.candidate = Arrays.copyOf(temp.candidate, temp.candidate.length - 1);
							candidateLen--;
						}
					}
				}
			}

			// �������
			int tempRow = temp.row / 3;
			int tempCol = temp.col / 3;
			// if (temp.row < 3 && temp.col < 3) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < candidateLen; k++) {
						if (temp.candidate[k] == sudoku[tempRow * 3 + i][tempCol * 3 + j]) {
							temp.candidate[k] = temp.candidate[temp.candidate.length - 1];
							temp.candidate = Arrays.copyOf(temp.candidate, temp.candidate.length - 1);
							candidateLen--;
						}
					}

				}
			}
			if (temp.next != null) {
				for (int r = 0; r < 9; r++) {
					/**
					 * ��֪��Ϊʲô���ı�ͷ�������飬����ڵ������Ҳ����ű䣬�������︳ֵһ�飻
					 */
					temp.next.candidate[r] = r + 1;
				}
			}
			temp = temp.next;
		}
	}
}
