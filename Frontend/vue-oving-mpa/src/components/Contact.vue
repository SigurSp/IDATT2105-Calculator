<script>
export default {
  props: ['contact'],
  computed: {
    isFormInvalid() {
      return !this.Name || !this.Email || !this.Message
    }
  },
  data() {
    return {
      Name : '',
      Email : '',
      Message : ''
    }
  },
  methods: {
    async formSubmitted() {
      if (!this.Name || !this.Email || !this.Message) {
        alert('Please fill in all fields');
        return;
      }

      const formData = {
        Name: this.Name,
        Email: this.Email,
        Message: this.Message
      };

      try {
        const response = await fetch('/feedback', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(formData),
        });
        if (response.ok) {
          const data = await response.json();
          console.log(data);
          alert('Form submitted');
        } else {
          alert('Failed to submit form');
        }
      } catch (error) {
        console.error('Error submitting form:', error);
        alert('Error submitting form');
      }

      localStorage.setItem('Name', this.Name);
      localStorage.setItem('Email', this.Email);
    }
  },
  created() {
    console.log('Contact component created');
    this.Name = localStorage.getItem('Name') || '';
    this.Email = localStorage.getItem('Email') || '';
  }
}
</script>

<template>
<div id = "contact">
  <div class="container mb-3">
    <form action="">
      <div class="mb-3">
        <label for="name" class="form-label">Name</label>
        <input type="text" class="form-control" id="name"
        v-model = "Name">
      </div>
      <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" v-model="Email">

      </div>
      <div class="mb-3">
        <label for="message" class="form-label">Message</label>
        <textarea class="form-control" id="message" rows="3" v-model="Message"></textarea>
      </div>
      <button
          type="submit"
          class="btn btn-primary"
          :disabled="isFormInvalid"
          @click.prevent="formSubmitted"
      >SubmitForm</button>
    </form>
  </div>
</div>
</template>

<style scoped>

</style>
