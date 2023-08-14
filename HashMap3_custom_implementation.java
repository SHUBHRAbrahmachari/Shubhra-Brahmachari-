
import java.util.ArrayList;
public class HashMap3_custom_implementation {
  private static class node<KEY,VALUE>{
      private KEY key;
      private VALUE value;
      protected node next;
      node(KEY key,VALUE value){
          this.key=key;
          this.value=value;
          next=null;
      }
      protected VALUE getValue(){
          return this.value;
      }
      protected KEY getKey(){
          return this.key;
      }
      protected void setValue(VALUE value){
          this.value=value;
      }
  }
  private static class set<KEY,VALUE>{
      private node<KEY,VALUE> head;
      set(){
          head=null;
      }
      protected boolean add(KEY key,VALUE value){                     // adds node if key is not present else updates value at that particular key
          node<KEY,VALUE> NEW = new node<KEY,VALUE>(key,value);
          if(head==null){
              head=NEW;
              return true;
          }else{
              node<KEY,VALUE> temp = head;
              node<KEY,VALUE> hold = head;
              while(temp!=null){
                  if(temp.getKey()==key){
                      temp.setValue(value);
                      return true;
                  }
                  hold=temp;
                  temp = temp.next;
              }
              hold.next=NEW;
              return true;
          }
      }
      protected VALUE erase(KEY key){
          if(head==null){
              return null;
          }else{
              if(head.getKey()==key){
                  VALUE val = head.getValue();
                  head=head.next;
                  return val;
              }else{
                  node<KEY,VALUE> temp=head;
                  while(temp.next!=null && temp.next.getKey()!=key){
                      temp=temp.next;
                  }
                  if(temp.next==null){
                      return null;
                  }else{
                      node<KEY,VALUE> tar = temp.next.next;
                      VALUE val = (VALUE) temp.next.getValue();
                      temp.next=tar;
                      return val;
                  }
              }
          }
      }
      protected VALUE get(KEY key){
          node<KEY,VALUE> temp = head;
          while (temp != null){
              if(temp.getKey()==key){
                  return temp.getValue();
              }
              temp=temp.next;
          }
          return null;
      }
      protected Integer size(){
          node<KEY,VALUE> temp = head;
          Integer count = Integer.valueOf(0);
          while(temp!=null){
              ++count;
              temp=temp.next;
          }
          return count;
      }
      protected node<KEY,VALUE> getNode(int index){
          if(index>=size()){
              return null;
          }
          node<KEY,VALUE> temp = head;
          for(int i=0;i<index-1;i++){
              temp=temp.next;
          }
          return temp;
      }
      protected void print(){
          node<KEY,VALUE> temp = head;
          while(temp!=null){
              System.out.print(" "+temp.getKey()+" : "+temp.getValue()+" \n");
              temp=temp.next;
          }
          return;
      }
  }
  private static class Hashmap<KEY,VALUE>{
      protected static final Double load_factor = 0.7500;
      private int capacity;
      private ArrayList<set<KEY,VALUE>> arr = new ArrayList<>(4);
      Hashmap(){
          capacity=4;
          for(int i=0;i<4;i++){
              set<KEY,VALUE> NEW = new set<>();
              arr.add(NEW);
          }
      }
      Hashmap(int capacity){
          this.capacity=capacity;
          for(int i=0;i<this.capacity;i++){
              set<KEY,VALUE> NEW = new set<>();
              arr.add(NEW);
          }
      }
      protected int size(){
          int count=0;
          for(int i=0;i<arr.size();++i){
              count+=arr.get(i).size();
          }
          return count;
      }
      protected void put(KEY key,VALUE value){
          if(size()==Math.floor(capacity*load_factor)){       // RE-HASHING process
              capacity*=2;
              ArrayList<set<KEY,VALUE>> NEW = new ArrayList<>();
              for(int i=0;i<capacity;i++){
                  set<KEY,VALUE> x = new set<>();
                  NEW.add(x);
              }
              for(int i=0;i<arr.size();i++){
                  set<KEY,VALUE> t = arr.get(i);
                  for(int i1=0;i1<t.size();i1++){
                      node<KEY,VALUE> data = t.getNode(i1);
                      KEY y = data.getKey();
                      VALUE z = data.getValue();
                      Integer val = (Math.abs(y.hashCode())) % capacity;
                      NEW.get(val).add(y,z);
                  }
              }
              arr = NEW;           // reference to 'arr' updated
          }
          Integer val = key.hashCode();
          val = (Math.abs(val)) % capacity;
          arr.get(val).add(key,value);
          return;
      }
      protected VALUE erase(KEY key){
          Integer x = Math.abs(key.hashCode()) % capacity;
          if(arr.get(x).size()==0){
              return null;
          }else{
              return arr.get(x).erase(key);
          }
      }
      protected void print(){
          for(int i=0;i<arr.size();i++){
              arr.get(i).print();
          }
          System.out.println();
          return;
      }
      protected VALUE get(KEY key){
          int val = (Math.abs(key.hashCode())) % capacity;
          return arr.get(val).get(key);
      }
  }

    public static void main(String[] args) {
         Hashmap<Integer,Character> st = new Hashmap<>(8);
         Character s = 'A';
         for(int i=1;i<=26;i++){
             st.put(i,s++);         // works fine in both roles , put(KEY key,VALUE value)
             System.out.println(" The current capacity is : " + st.capacity);
         }
         st.print();
        System.out.println(st.size());
        System.out.println(st.erase(2));
        System.out.println(st.erase(1));
        System.out.println(st.erase(56));
        System.out.println(st.erase(29));        //  ----->>> EACH CASE WORKING FINE , function erase(KEY key)
        System.out.println(st.erase(19));
        System.out.println(st.erase(26));
        System.out.println(st.erase(9));
        System.out.println(st.erase(100));

        System.out.println(st.get(1));
        System.out.println(st.get(100));
        System.out.println(st.get(19));
        System.out.println(st.get(29));        // ---------->> each case working fine , function get(KEY key)
        System.out.println(st.get(17));
        System.out.println(st.get(10));
        System.out.println(st.get(27));
        System.out.println(st.get(7));
  }
}
