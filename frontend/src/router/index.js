import Home from '../components/Home.vue'
import Header from '../components/shared/Header.vue'
import ItemBrowse from '../components/ItemBrowse.vue'
import ModalForm from '../components/ModalForm.vue'
import RegisterForm from '../components/RegisterForm.vue'
import ItemAdd from '../components/ItemAdd.vue'

export const routes = [
  {
    path: '', name: 'home', components: {
      default: Home,
      'header-top': Header
    }
  },
  {
    path: '/login', name: 'login', components: {
      default: ModalForm
    }
  },
  {path: '/browse', name: 'browse', component: ItemBrowse},
  {path: '/register', name: 'register', component: RegisterForm},
  {path: '/additem', name: 'additem', component: ItemAdd},
  {path: '*', redirect: '/'}
];
