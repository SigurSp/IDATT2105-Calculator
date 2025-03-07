describe('Form Validation', () => {
    beforeEach(() => {
        cy.visit('http://localhost:8080/?#/contact'); // Naviger til kontaktskjema-siden
    });

    it('disables submit button when fields are invalid', () => {
        cy.get('button[type="submit"]').should('be.disabled');

        cy.get('#name').type('John Doe');
        cy.get('button[type="submit"]').should('be.disabled');

        cy.get('#email').type('john.doe@example.com');
        cy.get('button[type="submit"]').should('be.disabled');
    });

    it('enables submit button when fields are valid', () => {
        cy.get('#name').type('John Doe');
        cy.get('#email').type('john.doe@example.com');
        cy.get('#message').type('Hello!');
        cy.get('button[type="submit"]').should('not.be.disabled');
    });
});
