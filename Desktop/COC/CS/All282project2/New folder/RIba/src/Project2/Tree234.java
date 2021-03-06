/* 
Computer Science 282: Advanced Data Structures.
Fall:2015
Professor: Chris Ferguson
Programmer: Luis Riba 
Project : Project2
Description: 
Programming Project #2 – 234 Trees and B Trees (10 Points)
For this project, we are going to start with code from the textbook and build on top of it.
In chapter 10 there is a fully functional 234 Tree program example, but it has several limitations. 
It does not store the tree to a disk drive and it does not support building B-Trees. 
For this project, you will add the disk storage capability, use Inheritance and code to support B-Trees.
Keep in mind, one very important goal of this project. 
Change the existing code from the book as little as possible! Do not rewrite the code. 
Develop the code as a new 'layer' of code. Use OOP Inheritance to build new classes from the existing classes. 
For example, when you add BTree support, write a new split() method in the new derived class, DO NOT modify the old one. 
In fact, keep it as part of the code so the program will support BOTH a BTree and a 234 Tree.
*/





package Project2;
public class Tree234 {
protected Node root = new Node(); // make root node
// -------------------------------------------------------------
public int find(long key)
{
Node curNode = root;
int childNumber;
while(true) {
if(( childNumber=curNode.findItem(key) ) != -1)
return childNumber; // found it
else if( curNode.isLeaf() )
return -1; // can't find it
else // search deeper
curNode = getNextChild(curNode, key);
} // end while
}
// -------------------------------------------------------------

//insert a DataItem
public void insert(long dValue)
{
	insert(dValue, "");
}

// insert a DataItem
public void insert(long dValue, String record)
{
Node curNode = root;
DataItem tempItem = new DataItem(dValue,record);
while(true)
{
if( curNode.isFull() ) // if node full,
{
split(curNode); // split it
curNode = curNode.getParent(); // back up
// search once
curNode = getNextChild(curNode, dValue);
} // end if(node is full)
else if( curNode.isLeaf() ) // if node is leaf,
break; // go insert
// node is not full, not a leaf; so go to lower level
else
curNode = getNextChild(curNode, dValue);
} // end while
curNode.insertItem(tempItem); // insert new DataItem
} // end insert()
// -------------------------------------------------------------
public void split(Node thisNode) // split the node
{
// assumes node is full
DataItem itemB, itemC;
Node parent, child2, child3;
int itemIndex;
itemC = thisNode.removeItem(); // remove items from
itemB = thisNode.removeItem(); // this node
child2 = thisNode.disconnectChild(2); // remove children
child3 = thisNode.disconnectChild(3); // from this node
Node newRight = new Node(); // make new node
if(thisNode==root) // if this is the root,
{
root = new Node(); // make new root
parent = root; // root is our parent
root.connectChild(0, thisNode); // connect to parent
}
else // this node not the root
parent = thisNode.getParent(); // get parent
// deal with parent
itemIndex = parent.insertItem(itemB); // item B to parent
int n = parent.getNumItems(); // total items?
for(int j=n-1; j>itemIndex; j--) // move parent's
{ // connections
Node temp = parent.disconnectChild(j); // one child
parent.connectChild(j+1, temp); // to the right
}
// connect newRight to parent
parent.connectChild(itemIndex+1, newRight);
// deal with newRight
newRight.insertItem(itemC); // item C to newRight
newRight.connectChild(0, child2); // connect to 0 and 1
newRight.connectChild(1, child3); // on newRight
} // end split()
// -------------------------------------------------------------
// gets appropriate child of node during search for value
public Node getNextChild(Node theNode, long theValue)
{
int j;
// assumes node is not empty, not full, not a leaf
int numItems = theNode.getNumItems();
for(j=0; j<numItems; j++) // for each item in node
{ // are we less?
if( theValue < theNode.getItem(j).dData )
return theNode.getChild(j); // return left child
} // end for // we're greater, so
return theNode.getChild(j); // return right child
}
// -------------------------------------------------------------
public void displayTree()
{
recDisplayTree(root, 0, 0);
}
// -------------------------------------------------------------
private void recDisplayTree(Node thisNode, int level,
int childNumber)
{
System.out.print("level="+level+" child="+childNumber+" ");
thisNode.displayNode(); // display this node
// call ourselves for each child of this node
int numItems = thisNode.getNumItems();
for(int j=0; j<numItems+1; j++)
{
Node nextNode = thisNode.getChild(j);
if(nextNode != null)
recDisplayTree(nextNode, level+1, j);
else
return;
}
} // end recDisplayTree()
// -------------------------------------------------------------\
} // end class Tree234
////////////////////////////////////////////////////////////////

