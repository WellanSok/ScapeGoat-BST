package structures;

import java.util.Iterator;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {
	private int upperBound;

	public BSTNode<T> parent(BSTNode<T> node,BSTNode<T> child){
		BSTNode<T> temp = null;
		/*if(node==child) {
			return node;
		}
		if(node.getLeft()==null||node.getRight()==null) {
			temp = null;
		}
		if(node.getLeft()==child||node.getRight()==child){
			temp = node;
		}
		if(node.getData().compareTo(child.getData())>0) {
			temp = parent(node.getLeft(),child);
		}
		if(node.getData().compareTo(child.getData())<0) {
			temp = parent(node.getRight(),child);
		}*/
		while(node!=null) {
			if(node.getData().compareTo(child.getData())>0) {
				temp = node;
				node = node.getLeft();
			}
			else if(node.getData().compareTo(child.getData())<0) {
				temp = node;
				node = node.getRight();
			}
			else {
				break;
			}
		}
		return temp;
	}
	
	@Override
	public void add(T t) {
		// TODO
		if(t==null) {
			throw new NullPointerException();
		}
		upperBound++;
		BSTNode<T> toAddn = new BSTNode<T>(t,null,null);
		root = addToSubtree(root,toAddn);
		
		if(height()>(Math.log(upperBound)/Math.log(1.5))) {
			BSTNode<T> part = parent(root,toAddn);
			while((double)subtreeSize(toAddn)/subtreeSize(part)<=(double)2/3) {
				part = parent(root,part);
				toAddn = parent(root,toAddn);
			}
			ScapegoatTree<T> sub = new ScapegoatTree<T>();
			sub.root = part;
			BSTNode<T> tt = parent(root,part);
			sub.balance();
			if(tt.getLeft()==part) {
				tt.setLeft(sub.root);
			}
			else {
				tt.setRight(sub.root);
			}
		}
	}

	@Override
	public boolean remove(T element) {
		// TODO
		if(element==null) {
			throw new NullPointerException();
		}
		boolean ret = contains(element);
		if(ret) {
			root = removeFromSubtree(root,element);
		}
		if(upperBound>size()*2) {
			this.balance();
			upperBound = size();
		}
		return ret;
	}
}
