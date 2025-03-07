import { mount } from '@vue/test-utils';
import Calculator from '@/components/Calculator.vue';

describe('Calculator Component', () => {
    test('renders correctly', () => {
        const wrapper = mount(Calculator);
        expect(wrapper.html()).toContain('History');
        wrapper.unmount();
    });

    test('performs addition correctly', async () => {
        const wrapper = mount(Calculator);
        await wrapper.find('#button-7').trigger('click'); // Click '7'
        await wrapper.find('#add').trigger('click'); // Click '+'
        await wrapper.find('#button-4').trigger('click'); // Click '4'
        await wrapper.find('button.equal').trigger('click');

        expect(wrapper.find('.result').element.value).toBe('11');
    });

    test('performs subtraction correctly', async () => {
        const wrapper = mount(Calculator);
        await wrapper.find('#button-9').trigger('click'); // Click '9'
        await wrapper.find('#subtract').trigger('click'); // Click '-'
        await wrapper.find('#button-5').trigger('click'); // Click '5'
        await wrapper.find('button.equal').trigger('click');

        expect(wrapper.find('.result').element.value).toBe('4');
    });

    test('performs multiplication correctly', async () => {
        const wrapper = mount(Calculator);
        await wrapper.find('#button-3').trigger('click'); // Click '3'
        await wrapper.find('#multiply').trigger('click'); // Click '*'
        await wrapper.find('#button-4').trigger('click'); // Click '4'
        await wrapper.find('button.equal').trigger('click');

        expect(wrapper.find('.result').element.value).toBe('12');
    });

    test('performs division correctly', async () => {
        const wrapper = mount(Calculator);
        await wrapper.find('#button-8').trigger('click'); // Click '8'
        await wrapper.find('#divide').trigger('click'); // Click '/'
        await wrapper.find('#button-2').trigger('click'); // Click '2'
        await wrapper.find('button.equal').trigger('click');

        expect(wrapper.find('.result').element.value).toBe('4');
    });

    test('handles division by zero', async () => {
        const wrapper = mount(Calculator);
        await wrapper.find('#button-8').trigger('click'); // Click '8'
        await wrapper.find('#divide').trigger('click'); // Click '/'
        await wrapper.find('#button-0').trigger('click'); // Click '0'
        await wrapper.find('button.equal').trigger('click');

        expect(wrapper.find('.result').element.value).toBe('Error');
    });

    test('clears the result correctly', async () => {
        const wrapper = mount(Calculator);
        await wrapper.find('#button-8').trigger('click'); // Click '8'
        await wrapper.find('#clear').trigger('click'); // Click 'C'

        expect(wrapper.find('.result').element.value).toBe('');
    });

    test('clears the history correctly', async () => {
        const wrapper = mount(Calculator);
        await wrapper.find('#button-8').trigger('click'); // Click '8'
        await wrapper.find('#add').trigger('click'); // Click '+'
        await wrapper.find('#button-2').trigger('click'); // Click '2'
        await wrapper.find('button.equal').trigger('click'); // Click '='
        await wrapper.find('#clearAll').trigger('click'); // Click 'CE'

        expect(wrapper.find('.result').element.value).toBe('');
        expect(wrapper.findAll('li').length).toBe(0);
    });
});
