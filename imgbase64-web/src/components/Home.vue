<template>
  <div>
    <h4>Encode image as Base64</h4>
    <hr>
    <label for="img-input">
      Image file:
    </label>
    <input
      ref="imgInput"
      id="img-input"
      class="form-control form-control-lg"
      type="file"
    >
    <label
      for="formatting"
      class="mt-3"
    >
      Formatting:
    </label>
    <input
      v-model="formatting"
      id="formatting"
      class="form-control"
    >
    <button
      class="btn btn-primary mt-3"
      @click="submitImage"
    >
      Submit
    </button>
    <hr>
    <label for="output">
      Output:
    </label>
    <textarea
      :value="output"
      id="output"
      class="form-control w-100"
      rows="15"
      readonly
    />
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Home",
  data () {
    return {
      imgFile: null,
      formatting: 'UTF-8',
      output: ''
    };
  },
  methods: {
    submitImage () {
      const data = new FormData();
      data.append('img', this.$refs.imgInput.files[0]);
      data.append('formatting', this.formatting);
      axios.post('/convert.ajx', data, {})
          .then(response => {
            if (response.data) {
              this.output = response.data;
            }
          });
    }
  }
}
</script>
