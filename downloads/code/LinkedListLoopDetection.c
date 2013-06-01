#include<stdio.h>
#include<stdlib.h>

// Functions
void listInitialize();
void detectLoop();

// List Node Struct
struct Node {

  struct Node * next;
  int info;

};

// Globals
struct Node * list;
int numberOfNodes;
int loopBegin; // This is the node where the loop starts in the list

int main() {
  
  listInitialize();
  detectLoop(); 

  return 0;
}

void detectLoop() {
  
  // Going to use two pointers to traverse the list.
  struct Node *iter1 = list, *iter2 = list;

  while(iter1->next != NULL) {

    iter1 = iter1->next;
    // If iter1 equals iter2 we know there is a loop
    // Also if iter1->next equals NULL we know there is no loop
    if(iter1 == iter2 || iter1->next == NULL)
      break;
    // Move iter a second time
    iter1 = iter1->next;

    // Check Equality Again
    if(iter1 == iter2)
      break;
    else
      // Move iter2 by one
      iter2 = iter2->next;

  }

  // If there is there is a NULL pointer we know we reached
  //   the end of the list
  if(iter1->next == NULL) 
    printf("%s\n","There was no loop in this Linked List.");
  // Otherwise the while loop broke because it found a loop
  //   in the Linked List
  else 
    printf("%s\n","There was a loop in the Linked List.");

  return;
}

/* 
 * Reads in file and initializes the list including a loop
 * if specified.
 */
void listInitialize () {
  char temp[256];
  int i;
  // This is to keep track of place in list
  struct Node *iter, *iter2;
  // Read in number of nodes
  fgets(temp,256,stdin);
  numberOfNodes = atoi(temp);
  // Read in loop beginning node
  fgets(temp,256,stdin);
  loopBegin = atoi(temp);

  for(i = 0; i < numberOfNodes; i++) {
    // Throw this guy on the heap
    if(i == 0) {
      iter = (struct Node *)malloc(sizeof(struct Node));
      list = iter;
    }
    iter->next = (struct Node *)malloc(sizeof(struct Node));
    fgets(temp,256,stdin);
    iter->info = atoi(temp);
    // Don't want to move to next on the last iteration
    if(i < numberOfNodes - 1) {
      iter = (*iter).next; 
    }
    else
      // Get rid of unnecessary allocation
      free(iter->next); 
  }


  // Negative Value means create no loop
  if(loopBegin >= 0) {
    // Initialize iter2 to head of the list
    iter2 = list;

    for(i = 0; i < loopBegin; i++) {
      // Get to the spot where we want to create the loop
      iter2 = iter2->next;
    }
    // Create the Loop
    iter->next = iter2;
  }
  else {
    // Create no Loop
    iter->next = NULL;
  }

  return;
}
