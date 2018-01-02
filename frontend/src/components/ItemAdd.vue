<template>
  <div>
    <div class="alert alert-danger" v-if="badData"> {{ message }}</div>
    <div class="alert alert-success" v-if="added">{{ message }}</div>
    <form enctype="multipart/form-data">
      <div class="form-group">
        <label for="title">Tytuł</label>
        <input type="text" class="form-control" id="title" v-model="advertData.title">
      </div>
      <div class="form-group">
        <label for="exampleFormControlSelect1">Wybierz kategorię</label>
        <select class="form-control" id="exampleFormControlSelect1">
          <option>1</option>
          <option>2</option>
          <option>3</option>
          <option>4</option>
          <option>5</option>
        </select>
      </div>
      <div class="form-group">
        <label for="description">Opis</label>
        <textarea class="form-control" rows="5" id="description" v-model="advertData.description"></textarea>
      </div>
      <div class="container">
        <div class="row">
          <div class="col-md-2" >
            <input type="file" multiple ref="imageInput">
          </div>
        </div>
  </div>
  <div class="text-center">
    <button class="btn btn-success" type="submit" @click.prevent="uploadAdvert">Dodaj</button>
    <button class="btn btn-danger">Anuluj</button>
  </div>
  </form>
  </div>
</template>

<style>
  .picture-container {
    position: relative;
    cursor: pointer;
    text-align: center;
  }
</style>
<script>
  import PictureInput from 'vue-picture-input'
  export default {
    data() {
      return {
        advertData: {
          title: '',
          description: '',
          duration: 1,
          images: [],
          imagesDescriptions: []
        },
        added: false,
        badData: false,
        message: ''
      }
    },
    components: {
      PictureInput
    },
    methods: {
      onChange(event) {
      },
      uploadAdvert() {
        var formData = new FormData();
        formData.append('title', this.advertData.title)
        formData.append('description', this.advertData.description)
        formData.append('duration', this.advertData.duration)
        for(var i = 0;i < this.$refs.imageInput.files.length; ++i) {
          formData.append('images', this.$refs.imageInput.files[0])
          formData.append('imagesDescriptions', 'Tego nie będzie.')
        }
        this.$http.post('http://localhost:8080/api/newadvert', formData).then(
          (response) => {
            this.added = true
            this.message = response.bodyText
          },
          (response) => {
            this.badData = true
            this.message = response.bodyText
          })
      },
    }
  }
</script>
