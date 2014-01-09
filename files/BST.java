import java.io.Serializable;

public class BST<T> implements Serializable {
    public class DNode<T> implements Serializable {
        DNode<T> left, right;
        T elem;

        public DNode(DNode<T> left, DNode<T> right, T elem) {
            this.left = left;
            this.right = right;
            this.elem = elem;
        }

        public DNode(T elem) {
            this.elem = elem;
            right = null;
            left = null;
        }

        public DNode(){
            elem = null;
            right = null;
            left = null;
        }
    }

    private DNode<T> root;
    StringBuilder sb;

    public BST(DNode<T> root){
        this.root = root;
    }

    public BST(){
        root = new DNode<T>();
    }

    public BST(T rootElem, BST<T> left, BST<T> right){
        root = new DNode<T>(left.root, right.root, rootElem);
    }

    public boolean isEmpty(){
        return root == null;
    }

    public BST<T> right(){
        return new BST<T>(root.right);
    }

    public BST<T> left(){
        return new BST<T>(root.left);
    }

    public T root(){
        if(!isEmpty()) return root.elem;
        else return null;
    }

    public BST(T rootElem){
        root = new DNode<T>(rootElem);
    }

    public void putRight(T elem){
        root.right = new DNode<T>(elem);
    }

    public void putLeft(T elem){
        root.left = new DNode<T>(elem);
    }

    public void setRoot(T elem){
        root.elem = elem;
    }

    private boolean exists(DNode<T> node, Comparable elem){
        if (node == null) {
            return false;
        } else if (elem.compareTo(node.elem) == 0) {
            return true;
        } else if (elem.compareTo(node.elem) < 0) {
            return exists(node.left, elem);
        } else {
            return exists(node.right, elem);
        }
    }

    public boolean exists(Comparable elem){
        return exists(root, elem);
    }

    public T search(Comparable elem) {
        T t = null;
        try {
            t = search(root, elem).elem;
        } catch (NoSuchFieldException e) {
            System.out.println("No such element!");
        }
        return t;
    }

    private DNode<T> search(DNode<T> node, Comparable elem) throws NoSuchFieldException {
        if (node == null) {
            throw new NoSuchFieldException();
        }
        if (elem.compareTo(node.elem) == 0) {
            return node;
        }
        if (elem.compareTo(node.elem) < 0) {
            return search(node.left, elem);
        } else {
            return search(node.right, elem);
        }
    }

    public void insert(Comparable<T> elem) {
        insert(root, elem);

    }

    private void insert(DNode node, Comparable elem) {
        if (node.elem == null)
            node.elem = elem;
        else if (elem.compareTo(node.elem) < 0) {
            if (node.left == null) {
                node.left = new DNode(elem);
            } else {
                insert(node.left, elem);
            }
        } else {
            if (node.right == null) {
                node.right = new DNode(elem);
            } else {
                insert(node.right, elem);
            }
        }
    }



    public boolean delete(Comparable elem) {
        try {
            DNode<T> node = search(root, elem);
            root = delete(root, elem);

        } catch (NoSuchFieldException e) {
            System.out.println("No such element!");
        } finally {
            try {
                DNode<T> node = search(root, elem);

            } catch (NoSuchFieldException e) {
                return false;
            }
        }
        return true;

    }

    private DNode<T> delete(DNode<T> node, Comparable elem) {
        if (elem.compareTo(node.elem) < 0)
            node.left = delete(node.left, elem);
        else if (elem.compareTo(node.elem) > 0)
            node.right = delete(node.right, elem);
        else if (node.left != null && node.right != null) {
            node.elem = (T) getMin(node.right).elem;
            node.right = deleteMin(node.right);
        } else if (node.left != null)
            node = node.left;
        else
            node = node.right;
        return node;
    }


    private DNode<T> deleteMin(DNode<T> node) {
        if (node.left != null)
            node.left = deleteMin(node.left);
        else
            node = node.right;
        return node;


    }


    private DNode<T> getMin(DNode<T> node) {
        DNode<T> n = node;
        while (n.left != null) {
            n = n.left;
        }
        return n;
    }

    private DNode<T> getMax(DNode<T> node) {
        DNode<T> n = node;
        while (n.right != null) {
            n = n.right;
        }
        return n;
    }

    @Override
    public String toString() {
        sb = new StringBuilder();
        inOrder();
        return sb.toString();
    }

    public void inOrder() {
        inOrder(this);
    }

    private void inOrder(BST<T> tree) {
        if (!tree.isEmpty()) {
            inOrder(tree.left());
            sb.append(tree.root() + "\n");
            inOrder(tree.right());
        }
    }


















}
