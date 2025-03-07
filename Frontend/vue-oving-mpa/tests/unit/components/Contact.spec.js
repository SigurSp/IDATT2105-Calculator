import { mount } from '@vue/test-utils';
import Contact from '@/components/Contact.vue'; // Bruk riktig sti

describe('ContactForm Component', () => {
    test('renders correctly', () => {
        const wrapper = mount(Contact);
        expect(wrapper.html()).toContain('Name');
        expect(wrapper.html()).toContain('Email');
        expect(wrapper.html()).toContain('Message');
    });

    test('submit button is disabled when fields are invalid', async () => {
        const wrapper = mount(Contact);
        wrapper.setData({ name: '', email: '', message: '' });
        await wrapper.vm.$nextTick(); // Wait for the Vue component to update
        expect(wrapper.find('button[type="submit"]').attributes('disabled')).toBe('');
    });

    test('enables submit button when fields are valid', async () => {
        const wrapper = mount(Contact);

        await wrapper.find('#name').setValue('John Doe');
        await wrapper.find('#email').setValue('john.doe@example.com');
        await wrapper.find('#message').setValue('Hello!');

        expect(wrapper.find('button[type="submit"]').attributes('disabled')).toBeUndefined();
    });

    test('shows alert on form submission', async () => {
        const wrapper = mount(Contact);

        await wrapper.find('#name').setValue('John Doe');
        await wrapper.find('#email').setValue('john.doe@example.com');
        await wrapper.find('#message').setValue('Hello!');

        global.alert = vi.fn(); // Mock alert
        await wrapper.find('button[type="submit"]').trigger('click');

        expect(global.alert).toHaveBeenCalledWith('Form submitted');
    });
});
