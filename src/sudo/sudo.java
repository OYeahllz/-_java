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
		// 获取数组
		int[][] sudoku = new int[9][9];
		sudoku = s;
		SudokuNode mysudo = new SudokuNode();
		// NodeOperation nodeOperation=new NodeOperation();
		// mysudo.head = null;
		int[] candidate = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		// 遍历所有数，把为0的数（即需要填的数）加入节点；
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku[i][j] == 0) {
					mysudo.addNode(i, j, sudoku[i][j], candidate);
					CountForNode++;
				}
			}
		}
		// 遍历节点
		NodeOperation(mysudo, CountForNode, sudoku);

//		//如果候选数只有一个，直接填掉
//		SudokuNode.Node temp = mysudo.head;
//		while(temp!=null) {
//			if(temp.candidate.length==1) {
//				sudoku[temp.row][temp.col]=temp.candidate[0];
//			}
//			temp=temp.next;
//		}		
		
		// 遍历完毕
		// 调用回溯函数
		SudokuNode.Node temp = mysudo.head;
		cotex(CountForNode, mysudo, sudoku, temp, startTime);
		// return;
	}

	// 输出函数
	public static void printArray(int[][] sudoku, long time) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(sudoku[i][j] + " ");
			}
			System.out.println();
		}
		// System.out.println();
		System.out.print("用时" + time + "us");
	}

	/**
	 * 回溯函数
	 */
	public static void cotex(int CountForNode, SudokuNode mysudo, int[][] sudoku, SudokuNode.Node temp,
			long startTime) {
		if (temp == null) {
			long endTime = System.nanoTime();

			printArray(sudoku, (endTime - startTime)/1000);
			return;// 输出
			// System.exit(0);
		} else {
			// 三步骤：填入，递归，回撤
			for (int i = 0; i < temp.candidate.length; i++) {
				// 每个点有temp.candidate.length个数可填
				// 填入

				// 判断该候选数可不可行，可行就填下一个，不可行，就回撤
				if (isOK(temp.row, temp.col, temp.candidate[i], sudoku)) {
					sudoku[temp.row][temp.col] = temp.candidate[i];
					// 可行，则填下一个空

					cotex(CountForNode, mysudo, sudoku, temp.next, startTime);
					if (temp != null)
						sudoku[temp.row][temp.col] = 0;
				}
			}
		}

	}

	// 排序，候选数最少的节点排第一

	public static boolean isOK(int row, int col, int value, int[][] sudoku) {
		// 遍历行，列
		for (int m = 0; m < 9; m++) {
			if (value == sudoku[row][m] || value == sudoku[m][col]) {
				return false;
			}
		}

		// 遍历宫格
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
		// 遍历候选数 行，列，宫格
		SudokuNode.Node temp = mysudo.head;
		int candidateLen = temp.candidate.length;

		for (int l = 0; l < CountForNode; l++) {

			candidateLen = temp.candidate.length;

			// 行遍历列遍历
			for (int i = 0; i < 9; i++) {
				if (sudoku[temp.row][i] != 0 || sudoku[i][temp.col] != 0) {
					for (int j = 0; j < candidateLen; j++) {
						if (temp.candidate[j] == sudoku[temp.row][i]) {
							temp.candidate[j] = temp.candidate[temp.candidate.length - 1];
							temp.candidate = Arrays.copyOf(temp.candidate, temp.candidate.length - 1);
							candidateLen--;
							continue;

							// 若该行有数，则删除相应候选数
						}
						if (temp.candidate[j] == sudoku[i][temp.col]) {
							temp.candidate[j] = temp.candidate[temp.candidate.length - 1];
							temp.candidate = Arrays.copyOf(temp.candidate, temp.candidate.length - 1);
							candidateLen--;
						}
					}
				}
			}

			// 宫格遍历
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
					 * 不知道为什么，改变头结点的数组，后面节点的数组也会跟着变，所以这里赋值一遍；
					 */
					temp.next.candidate[r] = r + 1;
				}
			}
			temp = temp.next;
		}
	}
}
