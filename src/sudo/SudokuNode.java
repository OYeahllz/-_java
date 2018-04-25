package sudo;

public class SudokuNode {
	Node head=null;
	class Node{
		Node next=null;
	int row;
	int col;
	int value;
	int[] candidate;
	public Node(int row,int col,int value,int[] candidate) {
		this.row=row;
		this.col=col;
		this.value=value;
		this.candidate=candidate;	
	}
	}
	
	//Ìí¼Ó½Úµã
	public void addNode(int row,int col,int value,int[] candidate){
		//int[] candidate2 = {1,2,3,4,5,6,7,8,9 };
        Node newNode =new Node(row, col,value,candidate);
        if(head==null){
            head=newNode;
            return;
        }
        Node temp = head;
        while (temp.next!=null)
        {
            temp=temp.next;
        }
        temp.next=newNode;
    }
}
