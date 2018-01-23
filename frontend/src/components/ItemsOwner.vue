<template>
  <div>
    <collapse v-model="show">
      <alert type="info">Aby zobaczyć oferty rozwiń jedno z ogłoszeń</alert>
    </collapse>
  <div class="panel-group">
    <div class="panel panel-default">
      <div class="panel-heading" role="button" @click="toggleAccordion(index, advertItem.id)" v-for="(advertItem, index) in userAdverts">
        <h4 class="panel-title clearfix">{{advertItem.title}}
          <button class="btn btn-success pull-right">Zobacz oferty</button>
        </h4>
        <collapse v-model="showAccordion[index]">
          <div class="panel-body">
            <ul class="list-group">
              <li class="list-group-item clearfix" v-for="off in offers">
                Oferta: {{off.offer}} zł
                <div class="pull-right">
                  <button class="btn btn-success" @click="acceptOfferAndShowModal(off.id)">Zaakceptuj</button>
                  <button class="btn btn-danger">Odrzuć</button>
                </div>
              </li>
            </ul>
          </div>
        </collapse>
      </div>
    </div>
  </div>
</div>
</template>
<script>
  import {mapActions} from 'vuex'
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        showAccordion: [],
        show: true
      }
    },
    computed: {
    ...mapGetters({
                    userAdverts: 'getUserAdverts',
                    offers: 'getOffers',
                    buyer: 'getBuyerData'
                  })
    },
    methods: {
      ...mapActions(['fetchUserAdverts',
                     'fetchOffers',
                     'acceptOffer']),
      toggleAccordion (index, id) {
        this.fetchOffers(id);
        if (this.showAccordion[index]) {
          this.$set(this.showAccordion, index, false)
        } else {
          this.showAccordion = this.showAccordion.map((v, i) => i === index)
        }
        if(this.showAccordion.includes(true)) {
          this.show = false
        }
        else {
          this.show = true
        }
      },
      acceptOfferAndShowModal(id) {
        this.acceptOffer(id);
        this.$modal.show('buyer')
      }
    },
    created() {
      this.fetchUserAdverts();
      var advertsCount = this.userAdverts.length;
      for (var i = 0; i < 10; ++i) {
        this.showAccordion.push(false);
      }
    }
  }
</script>
