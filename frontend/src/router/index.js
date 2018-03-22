import Home from '../components/Home.vue';
import Header from '../components/shared/Header.vue';
import ItemBrowse from '../components/ItemBrowse.vue';
import ModalForm from '../components/ModalForm.vue';
import RegisterForm from '../components/RegisterForm.vue';
import ItemAdd from '../components/ItemAdd.vue';
import ItemView from '../components/ItemView.vue';
import ItemOwner from '../components/ItemsOwner.vue';
import ItemRandom from '../components/ItemRandom.vue';
import Vue from 'vue';
import toasted from 'vue-toasted';
import axios from 'axios';

Vue.use(toasted);

const notAuthorizedUserHandler = (to, from, next) => {
  const config = {
    position: 'bottom-center',
    singleton: true,
    duration: 1500
  };

  axios.get('/hellosecure')
    .then(() => {
      next()
    })
    .catch(() => {
      Vue.toasted.error("Musisz być zalogowany", config);
      next('/')
    })
};

export const routes = [
  {
    path: '', name: 'home', components: {
      default: Home,
      'header-top': Header
    },
    beforeEnter: (to, from, next) => {
      axios.get('/hellosecure')
        .then(() => {
          next()
        })
        .catch(() => {
          Vue.cookie.delete('login');
          next()
        })
    }
  },
  {
    path: '/login', name: 'login', components: {
      default: ModalForm
    }
  },
  {path: '/browse', name: 'browse', component: ItemBrowse},
  {path: '/register', name: 'register', component: RegisterForm},
  {path: '/additem', name: 'additem', component: ItemAdd, beforeEnter: notAuthorizedUserHandler },
  {path: '/item/:id', name: 'item', component: ItemView},
  {path: '/youritems', name: 'youritems', component: ItemOwner, beforeEnter: notAuthorizedUserHandler },
  {path: '/random', name: 'randomadvert', component: ItemRandom},
  {path: '*', redirect: '/'}
];
