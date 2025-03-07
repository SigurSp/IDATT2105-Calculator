describe('Successful Submission', () => {
    beforeEach(() => {
        cy.visit('http://localhost:8080/?#/contact'); // Navigate to the contact form page
    });

    it('submits form successfully', () => {
        cy.get('#name').type('John Doe');
        cy.get('#email').type('john.doe@example.com');
        cy.get('#message').type('Hello!');

        // Mock the POST request
        cy.intercept('POST', '/feedback', { statusCode: 200, body: { message: 'success' } }).as('submitForm');

        cy.wait(1000)

        // Capture the alert
        cy.on('window:alert', (str) => {
            expect(str).to.equal('Form submitted');
        });

        // Submit the form
        cy.get('button[type="submit"]').click();


    });
});
