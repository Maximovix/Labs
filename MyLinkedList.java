public class MyLinkedList<E> {
    private Wrapper<E> first;
    private Wrapper<E> last;
    private int size = 0;

    public MyLinkedList(){
        last = new Wrapper<>(null,null,first);
        first =  new Wrapper<>(null,last,null);
    }

    public void addLast(E e){
        Wrapper<E> prev = last;
        prev.setCurrentElement(e);
        last = new Wrapper<>(null,null,prev);
        prev.setNextElement(last);
        size ++;
    }

    public void addFirst(E e){
        Wrapper<E> next = first;
        next.setCurrentElement(e);
        first = new Wrapper<>(null,next,null);
        next.setPrevElement(first);
        size++;
    }

    public E get(int count){
        Wrapper<E> target = first.getNextElement();
        for (int i = 0; i < count; i++){
            target = getNextElem(target);
        }
        return target.getCurrentElement();
    }

    private Wrapper<E> getNextElem(Wrapper<E> current){
        return current.getNextElement();
    }

    public int size(){
        return size;
    }

    private class Wrapper<E> {
        private E currentElement;
        private Wrapper<E> nextElement;
        private Wrapper<E> prevElement;

        private Wrapper(E currentElement, Wrapper<E> nextElement, Wrapper<E> prevElement) {
            this.currentElement = currentElement;
            this.nextElement = nextElement;
            this.prevElement = prevElement;
        }

        public E getCurrentElement(){
            return currentElement;
        }
        public void setCurrentElement(E currentElement){
            this.currentElement = currentElement;
        }
        public Wrapper<E> getNextElement(){
            return nextElement;
        }
        public void setNextElement(Wrapper<E> nextElement){
            this.nextElement = nextElement;
        }
        public Wrapper<E> getPrevElement(){
            return prevElement;
        }
        public void setPrevElement(Wrapper<E> prevElement){
            this.prevElement = prevElement;
        }
    }
}

