describe('My First Test', () => {
    it('GET', function() {
        cy.request({
            method: 'GET',
            url: 'localhost:8080/user/stats/pagination/0/10',
        }).then(function(response) {
            expect(response.body).have.property('content');
    });
  });
});

describe('My Second Test', () => {
    it('GET', function() {
        cy.request({
            method: 'GET',
            url: 'localhost:8080/fans/stats/pagination/0/10',
        }).then(function(response) {
            expect(response.body).have.property('content');
    });
  });
});

describe('My Third Test', () => {
    it('GET', function() {
        cy.request({
            method: 'GET',
            url: 'localhost:8080/leagues/stats/pagination/0/10',
        }).then(function(response) {
            expect(response.body).have.property('content');
    });
  });
});