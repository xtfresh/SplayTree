//Montek Singh
import java.util.LinkedList;
import java.util.List;

public class SplayTree<T extends Comparable<? super T>> {

	private Node<T> root;

	private int size;


	/**
	 * Splay the node containing data after adding
	 * 
	 * @param data
	 */
	public void add(T data) {
        try {
            Node<T> newNode = new Node<T>(data);
            if(getRoot() == null){
                setRoot(newNode);
                size++;
                return;
            }
            addHelper(newNode, getRoot());
            splayTree(newNode);

        } catch (Exception e) {
            e.printStackTrace();  
        }
    }
	
	private void addHelper(Node<T> nodeAdd, Node<T> current){
        try {
            if (nodeAdd.getData() != null) {
                if(current.getData() != null){
                    if(nodeAdd.getData().compareTo(current.getData()) < 0){
                        if(current.getLeft() == null){
                            current.setLeft(nodeAdd);
                            nodeAdd.setParent(current);
                            size++;
                        }
                        else
                            addHelper(nodeAdd, current.getLeft());
                    }
                    else if(nodeAdd.getData().compareTo(current.getData()) > 0){
                        if(current.getRight() == null){
                            current.setRight(nodeAdd);
                            nodeAdd.setParent(current);
                            size++;
                        }
                        else
                            addHelper(nodeAdd, current.getRight());
                    }
                }
                else{
                    if(current.getLeft() == null){
                        current.setLeft(nodeAdd);
                        nodeAdd.setParent(current);
                        size++;
                        return;
                    }
                    else{
                        addHelper(nodeAdd, current.getLeft());
                    }
                }
            }
            else{
                if(current.getRight() == null){
                    current.setRight(nodeAdd);
                    nodeAdd.setParent(current);
                    size++;
                }
                else{
                    addHelper(nodeAdd, current.getRight());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	/**
	 * Splay the parent of the node removed, do nothing
	 * if o is not in the tree, or was the root
	 * 
	 * @param o
	 * @return
	 */
	public T remove(T data) {
        try {
            if(getRoot() == null)
                return null;
            Node<T> tempNode = getRoot();
            Node<T> prevNode = getRoot();
            if(data == null){
                while(tempNode != null){
                    if(tempNode.getData() == null){
                        if(tempNode == getRoot()){
                            if(tempNode.getLeft() == null)
                                clear();
                            else{
                                setRoot(tempNode.getLeft());
                                getRoot().setParent(null);
                                size--;
                            }
                        }
                        else{
                            if(tempNode.getLeft() == null){
                                tempNode.setParent(null);
                                prevNode.setRight(null);
                            }
                            else{
                                prevNode.setRight(tempNode.getLeft());
                                tempNode.setParent(null);
                                prevNode.getRight().setParent(prevNode);
                                tempNode.setLeft(null);
                            }
                            size--;
                            splayTree(prevNode);
                        }
                        return null;
                    }
                    else{
                        prevNode = tempNode;
                        tempNode = tempNode.getRight();
                    }
                }
            }
            else{
                while(tempNode != null){
                    if(data.compareTo(tempNode.getData()) > 0){
                        prevNode = tempNode;
                        tempNode = tempNode.getRight();
                    }
                    else if(data.compareTo(tempNode.getData()) < 0){
                        prevNode = tempNode;
                        tempNode = tempNode.getLeft();
                    }
                    else{
                        if(tempNode.getLeft() == null && tempNode.getRight() == null){
                            if(prevNode.getLeft() == tempNode)
                                prevNode.setLeft(null);
                            else if(prevNode.getRight() == tempNode)
                                prevNode.setRight(null);
                            else if(prevNode == tempNode){
                                setRoot(null);
                            }

                            size--;
                            tempNode.setParent(null);
                            splayTree(prevNode);
                        }
                        else if(tempNode.getLeft() == null || tempNode.getRight() == null){
                            if(tempNode.getLeft() == null){
                                if(prevNode.getRight() == tempNode){
                                    prevNode.setRight(tempNode.getRight());
                                    prevNode.getRight().setParent(prevNode);
                                }
                                else{
                                    prevNode.setLeft(tempNode.getRight());
                                    prevNode.getLeft().setParent(prevNode);
                                }
                            }
                            else {
                                if(prevNode.getRight() == tempNode){
                                    prevNode.setRight(tempNode.getLeft());
                                    prevNode.getRight().setParent(prevNode);
                                }
                                else{
                                    prevNode.setLeft(tempNode.getLeft());
                                    prevNode.getLeft().setParent(prevNode);
                                }
                            }
                            tempNode.setLeft(null);
                            tempNode.setParent(null);
                            size--;
                            splayTree(prevNode);
                        }
                        else
                            removeHelper(prevNode, tempNode);

                        return data;
                    }
                }
            }
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	private void removeHelper(Node<T> prev, Node<T> temp){
        Node<T> parent = temp.getParent();
        T max = temp.getLeft().getData();
        Node<T> tempMoveHelper = temp.getLeft();
        Node<T> predecessor = tempMoveHelper;
        while(tempMoveHelper.getRight() != null){
            predecessor = tempMoveHelper;
            tempMoveHelper = tempMoveHelper.getRight();
            max = tempMoveHelper.getData();
        }
        if(tempMoveHelper == temp.getLeft()){
            temp.setData(max);
            if(tempMoveHelper.getLeft() == null){
                temp.setLeft(null);
                tempMoveHelper.setParent(null);
            }
            else{
                temp.setLeft(tempMoveHelper.getLeft());
                temp.getLeft().setParent(temp);
                tempMoveHelper.setLeft(null);
                tempMoveHelper.setParent(null);
            }
            size--;
            splayTree(temp);
            return;
        }
        temp.setData(max);
        if(tempMoveHelper.getLeft() == null){
            predecessor.setRight(null);
            tempMoveHelper.setParent(null);
        }
        else{
            predecessor.setRight(tempMoveHelper.getLeft());
            tempMoveHelper.setLeft(null);
            predecessor.getRight().setParent(predecessor);
            tempMoveHelper.setParent(null);
        }
        size--;
        if(parent != null)
            splayTree(parent);
    }

	/**
	 * Splay the object found matching the data, do nothing
	 * if o is not in the tree
	 * 
	 * @param o
	 * @return
	 */
	public T get(T data) {
        try {
            if(getRoot() == null)
                return null;
            Node<T> temp = getRoot();
            do{
                if(data.compareTo(temp.getData()) == 0){
                    splayTree(temp);
                    return data;
                }
                if(data.compareTo(temp.getData()) < 0)
                    temp = temp.getLeft();
                else
                    temp = temp.getRight();

            }while(temp != null);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	/**
	 * Splay the object found matching the data, do nothing if
	 * o is not in the tree
	 * 
	 * @param o
	 * @return
	 */
	public boolean contains(T data) {
        try {
            boolean bool = false;
            Node<T> temp = getRoot();
            if(temp == null)
                return false;
            if(data != null) {
                while(temp != null){
                    if(temp.getData() != null){
                        if(data.compareTo(temp.getData()) == 0){
                            bool = true;
                            break;
                        }
                        else if(data.compareTo(temp.getData()) < 0)
                            temp = temp.getLeft();
                        else
                            temp = temp.getRight();
                    }
                    else{
                        if(temp.getLeft() != null)
                            temp = temp.getLeft();
                        else
                            break;
                    }
                }
            }
            else{
                while(temp != null){
                    if(temp.getData() == null){
                        bool = true;
                        break;
                    }
                    temp = temp.getRight();
                }
            }
            if(bool == true)
                splayTree(temp);
            return bool;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (getRoot() == null);
	}

    public void clear() {
        setRoot(null);
        size = 0;
    }

    private void splayTree(Node<T> temp){
        try {
            if(temp == null || getRoot() == null)
                return;
            if(temp != getRoot()){
                if (temp.getParent() != null) {
                    if(temp.getParent() == getRoot())
                        zig(temp);
                    else{
                        if((temp.getParent().getLeft() == temp && temp.getParent().getParent().getLeft() == temp.getParent()) || (temp.getParent().getRight() == temp && temp.getParent().getParent().getRight() == temp.getParent()))
                            zigzig(temp);
                        else{
                            if((temp.getParent().getLeft() == temp && temp.getParent().getParent().getRight() == temp.getParent()) || (temp.getParent().getRight() == temp && temp.getParent().getParent().getLeft() == temp.getParent())){
                                zigzag(temp);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void zig(Node<T> temp){
        Node<T> parent = temp.getParent();
        Node<T> gp = (parent == null) ? null : parent.getParent();

        if (parent.getLeft() == temp)
        {
            parent.setLeft(temp.getRight());
            if (temp.getRight() != null)
                temp.getRight().setParent(parent);
            temp.setRight(parent);
        }
        else
        {
            parent.setRight(temp.getLeft());
            if (temp.getLeft() != null)
                temp.getLeft().setParent(parent);
            temp.setLeft(parent);
        }

        parent.setParent(temp);
        temp.setParent(gp);
        if (gp == null)
            root = temp;

    }

    private void zigzig(Node<T> temp){

        zig(temp.getParent());
        zig(temp);
    }

    private void zigzag(Node<T> temp){

        if (temp != null) {
            if(temp.getParent().getRight() == temp){

                Node<T> gp = temp.getParent().getParent();
                Node<T> p = temp.getParent();
                p.setRight(temp.getLeft());
                if(p.getRight() != null)
                    p.getRight().setParent(p);
                temp.setLeft(p);
                p.setParent(temp);
                gp.setLeft(temp.getRight());
                if(gp.getLeft() != null)
                    gp.getLeft().setParent(gp);
                temp.setRight(gp);
                Node<T> upper = gp.getParent();
                boolean onLeft = false;
                if(upper != null){
                    if(upper.getLeft() == gp)
                        onLeft = true;
                }
                gp.setParent(temp);

                if(getRoot() == gp){
                    setRoot(temp);
                    temp.setParent(null);
                }
                else{
                    if(upper != null){
                        if(onLeft)
                            upper.setLeft(temp);
                        else
                            upper.setRight(temp);
                        temp.setParent(upper);
                    }
                    splayTree(temp);
                }

            }
            else if(temp.getParent().getLeft() == temp){
                Node<T> gp = temp.getParent().getParent();
                Node<T> p = temp.getParent();
                p.setLeft(temp.getRight());
                if(p.getLeft() != null)
                    p.getLeft().setParent(p);
                temp.setRight(p);
                p.setParent(temp);
                gp.setRight(temp.getLeft());
                if(gp.getRight() != null)
                    gp.getRight().setParent(gp);
                temp.setLeft(gp);
                temp.setParent(gp.getParent());
                gp.setParent(temp);

                if(getRoot() == gp){
                    setRoot(temp);
                    temp.setParent(null);
                }
                else{
                    splayTree(temp);
                }
            }

        }


    }


    private Node<T> left(Node<T> temp, Node<T> tempRoot) {
        if (temp != null) {
            boolean p_root = false;
            Node<T> p = temp.getParent();
            if(p == getRoot())
                p_root = true;
            p.setRight(temp.getLeft());
            if(temp.getLeft() != null)
                temp.getLeft().setParent(p);
            temp.setLeft(p);
            if(p.getParent() != null){
                if(p.getParent().getLeft() == p){
                    p.setRight(p.getParent());
                    p.getRight().setParent(p);
                }
                else{
                    p.setLeft(p.getParent());
                    p.getLeft().setParent(p);
                }

            }
            temp.setParent(p.getParent());
            p.setParent(temp);
            if(p_root)
                return temp;
            else
                return tempRoot;
        }
        return null;
    }

    private Node<T> right(Node<T> temp, Node<T> tempRoot) {
        if (temp != null) {
            boolean p_root = false;
            Node<T> p = temp.getParent();
            if (p != null) {
                if(p == getRoot())
                    p_root = true;
                p.setLeft(temp.getRight());
                if(temp.getRight() != null)
                    temp.getRight().setParent(p);
                temp.setRight(p);
                if(p.getParent() != null){
                    if(p.getParent().getRight() == p){
                        p.setRight(p.getParent());
                        p.getRight().setParent(p);
                    }
                    else{
                        p.setLeft(p.getParent());
                        p.getLeft().setParent(p);
                    }
                }
                temp.setParent(p.getParent());
                p.setParent(temp);
                if(p_root)
                    return temp;
                else
                    return tempRoot;
            }
        }
        return null;
    }

	/*
	 * These methods are for grading, don't modify them
	 */

	public void setSize(int size) {
		this.size = size;
	}

	public Node<T> getRoot() { 
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public static class Node<E extends Comparable<? super E>> implements Comparable<Node<E>>{

		private Node<E> parent, left, right;
		private E data;

		public Node(E data) {
			this.data = data;
		}

		public Node<E> getParent() {
			return parent;
		}

		public void setParent(Node<E> parent) {
			this.parent = parent;
		}

		public Node<E> getLeft() {
			return left;
		}

		public void setLeft(Node<E> left) {
			this.left = left;
		}

		public Node<E> getRight() {
			return right;
		}

		public void setRight(Node<E> right) {
			this.right = right;
		}

		public E getData() {
			return data;
		}
		
		public void setData(E data) {
			this.data = data;
		}
		
		@Override
		public int compareTo(Node<E> tht) {
			if (data == null && tht.data == null) return 0;
			if (tht.data == null) return -1332;
			if (data == null) return 1332;
			return data.compareTo(tht.data);
		}
	}

}
