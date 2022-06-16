//
// Created by bogdan on 3/19/2022.
//

#include "Tests.h"
#include "Repository.h"
#include "Service.h"
#include <assert.h>
#include "Comparator.h"
#include "iostream"

void Test::test_all(){
    test_movie();
    //test_DynamicArray();
//    test_Repository();
//    test_Service();
//    test_add();
//    test_watchlist();
}

void Test::test_movie(){
    Movie mov{"Before I met you", "Romance", 2014, 50000, "www.trailer.ro"};

    list<Movie>movs;
    movs.push_back(Movie("Zebra", "IDK", 1000, 1000, "scutfita rosie"));
    movs.push_back(Movie("IDK", "IDK", 100, 1000, "www.ro"));
    movs.push_back(Movie("IK", "IK", 5, 10000, "www.ro"));

    CompareAscendingByTitle<Movie>cmp;
    movs.sort(cmp);
    for(auto s : movs){
        assert(s.get_title() == "IDK");
        break;
    }
}

//void Test::test_DynamicArray(){

//    DynamicArray<int>a;
//    a.add(1);
//    a.add(2);
//
//    assert(a.size() == 2);
//    assert(a.get_element(0) == 1);
//
//    DynamicArray<int>a2 = a;
//
//
//    assert(a2.size() == 2);
//
//    a2.remove(1);
//    assert(a2.get_element(0) == 2);
//
//    a2.update(3, 0);
//    assert(a2.get_element(0) == 3);
//
//    assert(a2.size() == 1);
//    a2.remove(7);
//    assert(a2.size() == 1);
//}

//void Test::test_Repository() {
//    Repository repo = Repository();
//    Movie mov = Movie("Before I met you", "Romance", 2014, 50000, "www.trailer.ro");
//    repo.repo_add_movie(mov);
//    int pos = repo.existing_movie("Before I met you");
//    assert(pos != -1);
//    pos = repo.existing_movie("Me before you");
//    assert(pos == -1);
//
//    //DynamicArray<Movie> movs = repo.get_movies();
//    vector<Movie> movs = repo.get_movies();
//    assert(movs.size() == 1);
//
//
//    Movie mov2 = Movie("Before I met you", "Something", 2000, 10000, "www.trailer.ro");
//
//    bool except;
//    try {
//        repo.repo_add_movie(mov2);
//    }
//    catch(...){
//        except = true;
//    }
//    assert(except == true);
//
//    //mov = Movie("Sausage party", "Something", 2000, 10000, "www.trailer.ro");
//    except = true;
//    try{
//        repo.repo_delete_movie(mov);
//    }
//    catch(...){
//        except = false;
//    }
//    assert(except == true);
//
//    mov2 = Movie("Baby drive", "Something", 2000, 10000, "www.trailer.ro");
//    try{
//        repo.repo_delete_movie(mov2);
//    }
//    catch(...){
//        except = true;
//    }
//    assert(except == true);
//
//
//    except = true;
//    try{
//        repo.repo_update_movie(mov2);
//    }
//    catch(...){
//        except = false;
//    }
//    assert(except == false);
//
//    except = true;
//    try {
//        Movie mov4 = Movie("Test", "Something", 2000, 10000, "www.trailer.ro");
//        repo.repo_add_movie(mov4);
//        Movie mov5 = Movie("Test", "Another Something", 2000, 10000, "www.trailer.ro");
//        repo.repo_update_movie(mov5);
//    }
//    catch (...){
//        except = false;
//    }
//    assert(except == true);
//}
//
//
//void Test::test_Service(){
//    Repository repo;
//    Service service{repo};
//    //Movie mov = Movie("Test", "Something", 2000, 10000, "www.trailer.ro");
//    bool except = false;
//    try{
//        service.add_movie("Test", "Something", 2000, 1000, "www.test.ro");
//    }
//    catch (...){
//        except = true;
//    }
//    assert(except == false);
//
//    try{
//        service.update_movie("Test", "Another Something", 2000, 1000, "www.testbun.ro");
//    }
//    catch (...){
//        except = true;
//    }
//    assert(except == false);
//
//    try{
//        service.delete_movie("Test");
//    }
//    catch (...){
//        except = true;
//    }
//    assert(except == false);
//
//    service.add_movie("Test", "Something", 2000, 1000, "www.test.ro");
//    //DynamicArray<Movie> a = service.get_all_movies();
//    vector<Movie> a = service.get_all_movies();
//    assert(a.size() == 1);
//
//}
//
//void Test::test_add(){
//    //DynamicArray<Movie> a;
//    vector<Movie> a;
//    Repository repo;
//    Service service{repo};
//    service.add_movie("Test", "Something", 2000, 1000, "www.test.ro");
//    a = service.get_all_movies();
//    assert(a.size() == 1);
////    a + Movie("Nou Test", "Nou Test", 200, 11, "www.ro");
////    assert(a.size() == 2);
////    assert(a.at(1).get_title() == "Nou Test");
////    Movie("Nou Test 3", "Nou Test", 200, 11, "www.ro") + a;
////    assert(a.size() == 3);
//}
//
//void Test::test_watchlist(){
//    Repository repo;
//    Service service{repo};
//
//    Movie mov = Movie("Test", "Something", 2000, 10000, "www.trailer.ro");
//    Movie mov2 = Movie("Test2", "Something2", 2000, 10000, "www.trailer.ro");
//    bool except = false;
//    try {
//        service.add_movie_watchlist(mov);
//    }
//    catch(...){
//        except = true;
//    }
//    assert(except == false);
//
//    service.delete_movie_watchlist("Test");
//    assert(service.get_watchlist().is_empty() == true);
//
//    mov = Movie("Test", "Something", 2000, 10000, "www.trailer.ro");
//    service.add_movie("Test", "Something", 2000, 10000, "www.ro");
//    service.add_movie("Test2", "Something2", 2000, 10000, "www.ro");
//
//    assert(service.filter("Something").get_size() == 1);
//    assert(service.filter("").get_size() == 2);
//
//    service.add_movie_watchlist(mov);
//    assert(service.existing_movie("Test") == true);
//
//    except = false;
//    try{
//        service.likes(mov);
//    }
//    catch(...){
//        except = true;
//    }
//    assert(except == false);
//
//    Watchlist wl = Watchlist();
//
//
//    except = false;
//    try{
//        wl.watchlist_play();
//    }
//    catch(...){
//        except = true;
//    }
//    assert(except == false);
//
//    wl.watchlist_add(mov);
//    except = false;
//    try{
//        wl.watchlist_play();
//    }
//    catch(...){
//        except = true;
//    }
//    assert(except == false);
//
//    wl.watchlist_add(mov2);
//    assert(wl.watchlist_get_current_movie() == mov);
//    wl.next();
//    assert(wl.watchlist_get_current_movie() == mov2);
//    wl.next();
//    assert(wl.watchlist_get_current_movie() == mov);
//
//    Movie mov3;
//    assert(wl.find_infos("Tes3") == mov3);
//
//    mov3 = Movie("Test3", "Something3", 2000, 10000, "www.trailer.ro");
//    assert(wl.find_movie(mov3) == -1);
//}