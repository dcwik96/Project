<template>
  <div>

    <form>
      <div class="form-group">
    <label for="title">Tytuł</label>
    <input type="text" class="form-control" id="title">
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
<textarea class="form-control" rows="5" id="description"></textarea>
</div>
  <div class="text-center">
  <div class="btn-group" v-for="x in 5" style="margin-right: 20px;">
<div class="picture-container">
    <div class="picture vcenter">
        <img style="width:50px;"  src="https://www.metatube.com/assets/metatube/video/img/Upload.svg" class="picture-src" id="wizardPicturePreview" title="">
        <input type="file" id="wizard-picture" class="">
    </div>
     <h6 class="">Dodaj zdjęcie</h6>


</div>

</div>
</div>
<div class="text-center">
<button class="btn btn-success">Dodaj</button>
<button class="btn btn-danger">Anuluj</button>
</div>

    </form>


</div>
</template>

<style>
/*Profile Pic Start*/
.picture-container{
    position: relative;
    cursor: pointer;
    text-align: center;
}
.picture{
    width: 106px;
    height: 106px;
    background-color: #999999;
    border: 4px solid #CCCCCC;
    color: #FFFFFF;
    margin: 0px auto;
    overflow: hidden;
    transition: all 0.2s;
    -webkit-transition: all 0.2s;
}
.picture:hover{
    border-color: #2ca8ff;
}
.content.ct-wizard-green .picture:hover{
    border-color: #05ae0e;
}
.content.ct-wizard-blue .picture:hover{
    border-color: #3472f7;
}
.content.ct-wizard-orange .picture:hover{
    border-color: #ff9500;
}
.content.ct-wizard-red .picture:hover{
    border-color: #ff3b30;
}
.picture input[type="file"] {
    cursor: pointer;
    display: block;
    height: 100%;
    left: 0;
    opacity: 0 !important;
    position: absolute;
    top: 0;
    width: 100%;
}

.picture-src{
    width: 100%;

}
/*Profile Pic End*/
</style>


<script>
export default {
  created() {
    $(document).ready(function(){
    // Prepare the preview for profile picture
        $("#wizard-picture").change(function(){
            readURL(this);
        });
    });
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#wizardPicturePreview').attr('src', e.target.result).fadeIn('slow');
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
  }
}
</script>
