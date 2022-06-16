//
// Created by bogdan on 3/19/2022.
//
#include "UI.h"
#include "Tests.h"

void auto_add(Repository &repo){
    Movie mov = Movie("A silent voice", "Anime", 2016, 118000, "https://www.youtube.com/watch?v=nfK6UgLra7g");
    repo.repo_add_movie(mov);

    mov = Movie("Inception", "Action", 2010, 119000, "https://www.youtube.com/watch?v=YoHD9XEInc0");
    repo.repo_add_movie(mov);

    mov = Movie("Exam", "Thriller", 2009, 8500, "https://www.youtube.com/watch?v=bkdt2Sygew0");
    repo.repo_add_movie(mov);

    mov = Movie("Shawshank Redemption", "Crime", 1994, 41000, "https://www.youtube.com/watch?v=6hB3S9bIaco");
    repo.repo_add_movie(mov);

    mov = Movie("Forrest Gump", "Comedy", 1994, 40000, "https://www.youtube.com/watch?v=bLvqoHBptjg");
    repo.repo_add_movie(mov);

    mov = Movie("Your name", "Anime", 2016, 94000, "https://www.youtube.com/watch?v=xU47nhruN-Q");
    repo.repo_add_movie(mov);

    mov = Movie("Morometii 2", "Family", 2018, 2100, "https://www.youtube.com/watch?v=DFxFDQyTs3Y");
    repo.repo_add_movie(mov);

    mov = Movie("Murder on the Orient Express", "Detective", 2017, 91000, "https://www.youtube.com/watch?v=Mq4m3yAoW8E");
    repo.repo_add_movie(mov);

    mov = Movie("Cars 2", "Animation", 2011, 26000, "https://www.youtube.com/watch?v=oFTfAdauCOo");
    repo.repo_add_movie(mov);

    mov = Movie("Cars", "Animation", 2006, 12000, "https://www.youtube.com/watch?v=SbXIj2T-_uk");
    repo.repo_add_movie(mov);
}

int main() {
    Test::test_all();
    Repository repository;
    auto_add(repository);
    Service service{repository};
    UI ui{service};
    ui.start();
    return 0;
}