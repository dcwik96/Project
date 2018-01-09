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
        <label for="timeChoose">Wybierz czas trwania</label>
        <div id="timeChoose">
        <div class="radio">
          <label><input type="radio" >Bezterminowy</label>
        </div>
        <div class="radio">
          <label><input type="radio">1 dzień</label>
        </div>
        <div class="radio disabled">
          <label><input type="radio">3 dni</label>
        </div>
        <div class="radio disabled">
          <label><input type="radio">7 dni</label>
        </div>
      </div>
      
      </div>
      <div class="form-group">
        <label for="description">Opis</label>
        <textarea class="form-control" rows="5" id="description" v-model="advertData.description" ></textarea>
      </div>
      <div class="container">
        <div class="row">
              <div v-if="!images[0]">
                <h2>Dodaj zdjęcia do przedmiotu</h2>
              </div>
              <div v-else>
                <div class="col-md-2" v-for="image in images" >
                <img  :src="image" class="img-preview"/>
                <!--<button @click="removeImage">Remove-->
                  <!--image</button>-->
                </div>
              </div>
          </div>
        <br>
        <input id="imageInput" type="file" @change="onFileChange" multiple>
  </div>
  <div class="text-center">
    <button class="btn btn-success" type="submit" @click.prevent="uploadAdvert">Dodaj</button>
    <button class="btn btn-danger">Anuluj</button>
  </div>
  </form>
  </div>
</template>

<style>
  .img-preview {
    width: 100px;
    height: 100px;
    padding: 10px;
    border: 5px #7f7f7f solid;
    display: inline-block;
  }
  button {

  }
</style>
<script>
  import PictureInput from 'vue-picture-input'
  export default {
    data() {
      return {
        images: [],
        advertData: {
          title: '',
          description: '',
          duration: 1,
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
      onFileChange(e) {
        this.images = [];
        var files = e.target.files || e.dataTransfer.files;
        if (!files.length)
          return;
        for(var i = 0; i < files.length; ++i)
          this.createImage(files[i]);
      },
      createImage(file) {
        var reader = new FileReader();
        var vm = this;

        reader.onload = (e) => {
          vm.images.push(e.target.result);
        };
        reader.readAsDataURL(file);
      },
      uploadAdvert() {
        var formData = new FormData();
        formData.append('title', this.advertData.title)
        formData.append('description', this.advertData.description)
        formData.append('duration', this.advertData.duration)
        // console.log(imageInput.files);
        for(var i = 0;i < imageInput.files.length; ++i) {
          formData.append('images', imageInput.files[i])
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
