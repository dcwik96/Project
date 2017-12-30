<template>
  <div>
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
          <div class="col-md-2" v-for="i in 5">
            <picture-input
              :ref="'pictureInput' + i"
              @change="onChange"
              width="150"
              height="150"
              margin="10"
              accept="image/jpeg,image/png"
              size="10"
              buttonClass="btn btn-primary"
              :customStrings="{
        upload: '<h1>Wyśli</h1>',
        drag: 'Przeciagnij i upuść zdjecię'
      }">
            </picture-input>
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
  import PictureInput from './PictureInput.vue'

  export default {
    data() {
      return {
        advertData: {
          title: 'test',
          description: 'test test test test',
          duration: 1,
          images: [],
          imagesDescriptions: []
        }
      }
    },
    components: {
      PictureInput
    },
    methods: {
      onChange() {
        console.log('New picture selected!')
        console.log('Picture loaded.')
        this.advertData.images.push(this.$refs.pictureInput1.imageAsFile)
      },
      onRemoved() {
      },
      uploadAdvert() {
        var form = new FormData();
        form.append("title", "asds");
        form.append("description", "sklfldasfjdsl");
        form.append("duration", "1");
        for (i = 0; i < 5; ++i) {
          form.append("images", this.$refs.pictureInput1.imageAsFile);
          form.append("imagesDescriptions", "fsadasddafkdslf");
        }


        this.$http.post('http://localhost:8080/api/newadvert', form)
      },
    }
  }
</script>
