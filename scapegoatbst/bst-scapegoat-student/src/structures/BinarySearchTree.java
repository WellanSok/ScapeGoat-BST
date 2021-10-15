package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) {
		// TODO
		return get(t)!=null;
	}

	public boolean remove(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	protected BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	public T get(T t) {
		// TODO
		if(t==null) {
			throw new NullPointerException();
		}
		return getHelp(t,root);
	}
	
	private T getHelp(T t, BSTNode<T> node) {
		if(node==null) {
			return null;
		}
		else if(t.compareTo(node.getData())<0) {
			return getHelp(t,node.getLeft());
		}
		else if(t.compareTo(node.getData())>0) {
			return getHelp(t,node.getRight());
		}
		else {
			return node.getData();
		}
	}


	public void add(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}
		int result = toAdd.getData().compareTo(node.getData());
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
		} else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
		}
		return node;
	}

	@Override
	public T getMinimum() {
		// TODO
		if(root==null) {
			return null;
		}
		if(root.getLeft()==null&&root.getRight()==null) {
			return root.getData();
		}
		BSTNode<T> temp = root;
		while(temp.getLeft()!=null) {
			temp = temp.getLeft();
		}
		return temp.getData();
	}


	@Override
	public T getMaximum() {
		// TODO
		if(root==null) {
			return null;
		}
		if(root.getLeft()==null&&root.getRight()==null) {
			return root.getData();
		}
		BSTNode<T> temp = root;
		while(temp.getRight()!=null) {
			temp = temp.getRight();
		}
		return temp.getData();
	}


	@Override
	public int height() {
		// TODO
		return helpHeight(root);
	}
	
	private int helpHeight(BSTNode<T> node) {
		if(node==null) {
			return -1;
		}
		else {
			int right = helpHeight(node.getRight());
			int left = helpHeight(node.getLeft());
			if(left>right) {
				return left +1;
			}
			else {
				return right+1;
			}
		}
	}


	public Iterator<T> preorderIterator() {
		// TODO
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue,root);
		return queue.iterator();
	}
	
	private void preorderTraverse(Queue<T> q,BSTNode<T> node) {
		if(node!=null) {
			q.add(node.getData());
			preorderTraverse(q,node.getLeft());
			preorderTraverse(q,node.getRight());
		}
	}

	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}


	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> postorderIterator() {
		// TODO
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue,root);
		return queue.iterator();
	}
	
	private void postorderTraverse(Queue<T> q,BSTNode<T> node) {
		if (node != null) {
			postorderTraverse(q, node.getLeft());
			postorderTraverse(q, node.getRight());
			q.add(node.getData());
		}
	}

	@Override
	public boolean equals(BSTInterface<T> other) {
		// TODO
		
		return equalHelp(root,other.getRoot());
	}
	
	private boolean equalHelp(BSTNode<T> main,BSTNode<T> two) {
		if(main==null&&two==null) {
			return true;
		}
		else if((main==null&&two!=null)||(main!=null&&two==null)) {
			return false;
		}
		else {
			int dataCheck = main.getData().compareTo(two.getData());
			boolean leftVal = equalHelp(main.getLeft(),two.getLeft());
			boolean rightVal = equalHelp(main.getRight(),two.getRight());
			if((leftVal&&rightVal)&&dataCheck==0) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	@Override
	public boolean sameValues(BSTInterface<T> other) {
		// TODO
		Iterator<T> first = this.inorderIterator();
		Iterator<T> second = other.inorderIterator();
		while(first.hasNext()&&second.hasNext()) {
			if(!(first.next().equals(second.next()))) {
				return false;
			}
		}
		//is this ok or do i need to do something else
		if(!first.hasNext()&&!second.hasNext()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isBalanced() {
		// TODO
		int height = height();
		double lower = Math.pow(2, height);
		double upper = Math.pow(2, height+1);
		int size = size();
		if((lower<=size)&&size<=upper) {
			return true;
		}
		return false;
	}

	@Override
    @SuppressWarnings("unchecked")

	public void balance() {
		// TODO
		T[] arr = (T[]) new Comparable[size()];
		int idx = 0;
		Iterator<T> in = inorderIterator();
		while(in.hasNext()) {
			arr[idx] = in.next();
			idx++;
		}
		//sortArray(arr,root,0);
		root = sort2BST(0,arr.length-1,arr);
	}
	
	private BSTNode<T> sort2BST(int lower,int upper,T[] array){
		if(lower>upper) {
			return null;
		}
		int mid = (lower+upper)/2;
		BSTNode<T> node = new BSTNode<T>(array[mid],sort2BST(lower,mid-1,array),sort2BST(mid+1,upper,array));
		//node.setLeft(sort2BST(lower,mid-1,array));
		//node.setRight(sort2BST(mid+1,upper,array));
		return node;
	}
	
	/*private void sortArray(T[] array,BSTNode<T> node,int idx) {
		if (node != null) {
			sortArray(array, node.getLeft(),idx);
			array[idx++] = node.getData();
			sortArray(array, node.getRight(),idx);
		}
		//return array;
	}*/
	


	@Override
	public BSTNode<T> getRoot() {
        // DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
				tree.add(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.remove(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			tree.add(r);
		}
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
		tree.balance();
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
	}
}