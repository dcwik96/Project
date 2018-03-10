<template>
  <div>
    <form enctype="multipart/form-data">
      <div class="form-group" enctype="multipart/form-data">
        <label for="title">Tytuł</label>
        <input type="text" class="form-control" id="title" v-model="advertData.title">
      </div>
      <div class="form-group">
        <label for="timeChoose">Wybierz czas trwania</label>
        <div id="timeChoose">
          <div class="radio">
            <label><input type="radio" name="timeChoose" value="30" v-model="advertData.duration">Bezterminowy</label>
          </div>
          <div class="radio">
            <label><input type="radio" name="timeChoose" value="1" v-model="advertData.duration">1 dzień</label>
          </div>
          <div class="radio">
            <label><input type="radio" name="timeChoose" value="3" v-model="advertData.duration">3 dni</label>
          </div>
          <div class="radio">
            <label><input type="radio" name="timeChoose" value="7" v-model="advertData.duration">7 dni</label>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label for="description">Opis</label>
        <textarea class="form-control" rows="5" id="description" v-model="advertData.description"></textarea>
      </div>
      <div class="container">
        <h1>Wybierz zdjęcia</h1>
        <div class="dropbox row text-center">
          <input
            type="file"
            multiple
            :name="uploadFieldName"
            :disabled="isSaving"
            @change="onFilesChange($event.target.name, $event.target.files); fileCount = $event.target.files.length"
            accept="image/*"
            class="input-file row">
          <div v-if="!images[0]">
            Wybierz lub upuść zdjęcia
          </div>
          <div class="col-md-2 text-center" v-for="image in images">
            <img :src="image" class="img-preview">
          </div>
        </div>
      </div>
      <div class="text-center">
        <button class="btn btn-success" type="submit" @click.prevent="upload">Dodaj</button>
        <button class="btn btn-danger" @click="reset">Anuluj</button>
      </div>
    </form>
  </div>
</template>
<style>
.dropbox {
  border: 2px dashed grey;
  border-offset: -10px;
  background: lightcyan;
  color: dimgray;
  padding: 10px 10px;
  min-height: 200px;
  position: relative;
  coursor: pointer;
  border-radius: 25px;
  margin-bottom: 10px;
}
.input-file {
  opacity: 0;
  width: 100%;
  height: 200px;
  position: absolute;
  coursor: pointer;
}

.dropbox:hover {
  background: lightblue;
}
.dropbox p {
  font-size: 1.2em;
  text-align: center;
  padding: 50px 0;
}
.img-preview {
  width: 100%;
  height: 170px;
  padding: 5px;
  display: inline-block;
  border: 2px dashed gray;
  border-radius: 25px;
}
</style>
<script>
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
        uploadError: null,
        uploadFieldName: 'images',
        isInitial: true,
        isSaving: false,
        dataToSent: null
      }
    },
    methods: {
      reset() {
        this.images = [];
        this.uploadError = null;
      },
      upload() {
        let config = {
          position: 'bottom-center',
          singleton: true,
          duration: 1500
        };
        this.$http.post('http://localhost:8080/api/newadvert', this.dataToSent).then(
          (response) => {
            this.$toasted.success(response.bodyText, config);
          },
          (response) => {
            this.$toasted.error(response.bodyText, config)
          })
      },
      onFilesChange(fieldName, fileList) {
        if(!fileList.length) return;
        this.isSaving = true;
        const formData = new FormData();https://www.startpage.com/do/search
        formData.append('title', this.advertData.title);
        formData.append('description', this.advertData.description);
        formData.append('duration', this.advertData.duration.toString());
        Array
          .from(Array(fileList.length).keys())
          .map(x => {
            formData.append(fieldName, fileList[x], fileList[x].name);
            formData.append('imagesDescriptions', 'Jakieś hashtagi');
            this.createImage(fileList[x]);
          });
        this.dataToSent = formData
      },
      createImage(file) {
        var reader = new FileReader();
        var vm = this;
        reader.onload = (e) => {
          vm.images.push(e.target.result);
        };
        reader.readAsDataURL(file);
      }
    }
  }
</script>
