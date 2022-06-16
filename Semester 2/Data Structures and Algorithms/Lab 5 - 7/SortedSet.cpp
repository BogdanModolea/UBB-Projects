#include "SortedSet.h"
#include "SortedSetIterator.h"

#include "iostream"
using namespace std;


SortedSet::SortedSet(Relation r) {
	this->length = 0;
    root = nullptr;
    this->rel = r;
}

BST* SortedSet::init_node(TElem elem){
    BST* node = new BST;
    node->info = elem;
    node->right = node->left = nullptr;
    return node;
}

bool SortedSet::add(TComp elem) {
	BST* new_node;
    BST* parent = new BST;

    new_node = root;

    if(root == nullptr){
        root = init_node(elem);
        length++;
        return true;
    }

    while (new_node && new_node->info != elem){
        parent = new_node;

        if(rel(elem, new_node->info))
            new_node = new_node->left;
        else
            new_node = new_node->right;
    }

    if(!new_node){
        new_node = init_node(elem);

        if(rel(elem, parent->info))
            parent->left = new_node;
        else
            parent->right = new_node;
        length++;
        return true;
    }
    return false;
}

BST* SortedSet::get_max(BST* root) {
    BST* now = root;
    BST* ans = root;

    while (now->right){
        ans = now;
        now = now->right;
    }

    return ans;
}

BST* SortedSet::delete_node(BST* root, TComp elem, bool& checked){
    if(!root)
        return root;

    if(root->info == elem){
        checked = true;

        if(root->left == nullptr && root->right == nullptr)
            return nullptr;

        if(root->left == nullptr)
            return root->right;
        if(root->right == nullptr)
            return root->left;

        BST* maxi = get_max(root->left);
        root->info = maxi->info;
        root->left = delete_node(root->left, maxi->info, checked);
    }
    else if(rel(root->info, elem)){
        root->right = delete_node(root->right, elem, checked);
    }
    else
        root->left = delete_node(root->left, elem, checked);

    return root;
}

bool SortedSet::remove(TComp elem) {
    bool checked = false;
    root = delete_node(root, elem, checked);
    if (checked)
        length--;

    return checked;
}


bool SortedSet::search(TComp elem) const {
    auto now = root;

    while (now) {
        if (now->info == elem)
            return true;
        if (rel(elem, now->info))
            now = now->left;
        else
            now = now->right;
    }
    return false;
}


int SortedSet::size() const {
    return length;
}



bool SortedSet::isEmpty() const {
    return length == 0;
}

SortedSetIterator SortedSet::iterator() const {
	return SortedSetIterator(*this);
}


SortedSet::~SortedSet() {
    delete root;
}


