package controller.tda.list;

import java.lang.reflect.Method;

import controller.tda.list.LinkedList;


public class LinkedList<E> {
    private Node<E> header; // Nodo cabecera (el primer nodo de la lista)
    private Node<E> last;   // Nodo último (el último nodo de la lista)
    private Integer size; // Tamaño de la lista (cuenta el número de nodos en la lista)
    public static Integer ASC = 1;
    public static Integer DESC = 0;

    // Constructor de la clase LinkedList
    public LinkedList() {
        this.header = null; // Inicialmente, la cabecera es nula (no hay nodos en la lista)
        this.last = null;   // Inicialmente, el último nodo es nulo
        this.size = 0;      // Inicialmente, el tamaño de la lista es 0
    }

    // Método para verificar si la lista está vacía
    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    // Método privado para agregar un elemento al principio de la lista
    private void addHeader(E dato) {
        Node<E> help;

        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            this.size++;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    public void addLast(E info) {  //cambio a public
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(info);
            header = help;
            last = help;
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
        }
        this.size++;
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }
        return header.getInfo();
    }

    public E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    
    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacía");
        }
    
        E removedData;
        if (size == 1) {
            removedData = header.getInfo();
            header = null;
            last = null;
        } else {
            Node<E> previousNode = getNode(size - 2);
            removedData = last.getInfo();
            previousNode.setNext(null);
            last = previousNode;
        }
    
        size--;
        return removedData;
    }

    public boolean remove(E element) {
        if (isEmpty()) return false;
        
        if (header.getInfo().equals(element)) { // Si el elemento está en la cabecera
            header = header.getNext();
            size--;
            return true;
        }
        
        Node<E> current = header;
        while (current.getNext() != null) {
            if (current.getNext().getInfo().equals(element)) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        
        return false; // Elemento no encontrado
    }
    

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" ->");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public Node<E> getHeader() {
        return header;
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public void update(E object, Integer index) {
        if (index < 0 || index >= size) { // Verifica el rango del índice
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
    
        Node<E> current = header; // Comienza desde la cabecera
        for (int i = 0; i < index; i++) {
            current = current.getNext(); // Moverse al nodo en el índice deseado
        }
    
        current.setInfo(object); // Actualizar el dato del nodo usando el setter
    }
    // metodos de ordenacion 
    // ordenacion por inserccion 
    public LinkedList<E> order(int orderType) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            
            for (int i = 1; i < lista.length; i++) {
                E aux = lista[i];
                int j = i - 1;
                
                // Llamada a compare con orderType
                while (j >= 0 && compare(lista[j], aux, orderType)) {
                    lista[j + 1] = lista[j];
                    j--;
                }
                lista[j + 1] = aux;
            }
            
            // Actualiza la lista con el arreglo ordenado
            this.toList(lista);
        }
        return this;
    }
    
    // Modificación en el método compare para admitir el parámetro orderType
    private Boolean compare(E a, E b, int orderType) {
        if (a instanceof Number) {
            Number a1 = (Number) a;
            Number b1 = (Number) b;
            // Ascendente (orderType == 1) o descendente (orderType == 2)
            return (orderType == 1) ? (a1.doubleValue() > b1.doubleValue()) : (a1.doubleValue() < b1.doubleValue());
        } else {
            return (orderType == 1) ? (a.toString().compareTo(b.toString()) > 0)
                    : (a.toString().compareTo(b.toString()) < 0);
        }
    }
    
    //Revisar este codex -->

    //private Object exist_attribute(E a, String attribute) throws Exception {
    //    Method method = null;
    //    attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(0, 1);
    //    attribute = "get" + attribute;
    //    for (Method aux : a.getClass().getMethods()) {
    //        if (aux.getName().contains(attribute)) {
    //            method = aux;
    //            break;
    //        }
    //    }
    //    if (method == null) {
    //        for (Method aux : a.getClass().getSuperclass().getMethods()) {
    //            if (aux.getName().contains(attribute)) {
    //                method = aux;
    //                break;
    //            }
    //        }
    //    }
    //}
    
}