import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

 //
export const asyncRoutes = [
  {
    path: '/system',
    component: Layout,
    redirect: '/system/test',
    name: 'System',
    alwaysShow: true,
    meta: {
      title: '系统管理',
      icon: 'nested',
      roles: ['admin','editor']
    },
    children: [
      {
        title: '权限管理',
        path: 'test',
        name: 'Test',
        component: () => import('@/views/system/index'),
        meta: { title: '权限修改', icon: 'table', roles: ['editor']}
      }
    ]
  },
  {
    path: '/案由管理2',
    component: Layout,
    alwaysShow: true,
    name: '案由管理2',
    meta: {
      title: '案由管理',
      icon: 'form',
      roles: ['admin','editor']
    },
    children: [
      {
        path: 'ayinfo',
        name: '案由信息管理',
        component: () => import('@/views/案由管理2/ayinfo'),
        meta: { title: '案由信息管理', icon: 'form', roles: ['admin','editor'] }
      },
      {
        path: 'ay',
        name: '案由文书管理',
        component: () => import('@/views/案由管理2/ay'),
        meta: { title: '案由文书管理', icon: 'form', roles: ['admin','editor'] }
      }
    ]
  },
  {
    path: '/案由配置',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '案由配置',
        component: () => import('@/views/案由配置/index'),
        meta: { title: '案由配置', icon: 'form', roles: ['admin','editor']}
      }
    ]
    
  },


  {
    path: '/子程序上传',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '子程序上传',
        component: () => import('@/views/子程序上传/index'),
        meta: { title: '子程序上传', icon: 'form', roles: ['admin','editor'] }
      }
    ]
  },

  {
    path: '/信息项抽取',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '信息项抽取',
        component: () => import('@/views/信息项抽取/index'),
        meta: { title: '信息项抽取', icon: 'form', roles: ['admin','editor'] }
      }
    ]
  },

  {
    path: '/查看信息项',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '查看信息项',
        component: () => import('@/views/查看信息项/index'),
        meta: { title: '查看信息项', icon: 'form', roles: ['admin', 'editor'] }
      }
    ]
  }, 

  // {
  //   path: '/子程序审核',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: '子程序审核',
  //       component: () => import('@/views/子程序审核/index'),
  //       meta: { title: '子程序审核', icon: 'form', roles: ['admin'] }
  //     }
  //   ]
  // },
  {
    path: '/管理信息项',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '管理信息项',
        component: () => import('@/views/管理信息项/index'),
        meta: { title: '管理信息项', icon: 'form', roles: ['admin', 'editor'] }
      }
    ]
  },

  {
    path: '/信息项管理',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '信息项管理',
        component: () => import('@/views/信息项管理/index'),
        meta: { title: '信息项管理', icon: 'form', roles: ['admin', 'editor'] }
      }
    ]
  },

  {
    path: '/案由管理',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '案由管理',
        component: () => import('@/views/案由管理/index'),
        meta: { title: '案由管理', icon: 'form', roles: ['admin'] }
      }
    ]
  }


  
];

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/文书抽取',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '文书抽取',
        component: () => import('@/views/文书抽取/index'),
        meta: { title: '文书抽取', icon: 'form'}
      }
    ]
  },



  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },

  {
    path: '/example',
    component: Layout,
    redirect: '/example/table',
    name: 'Example',
    meta: { title: 'Example', icon: 'example' },
    children: [
      {
        path: 'table',
        name: 'Table',
        component: () => import('@/views/table/index'),
        meta: { title: 'Table', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: 'Tree', icon: 'tree' }
      }
    ]
  },

  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: 'Form', icon: 'form' }
      }
    ]
  },

  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'Nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'Menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: { title: 'Menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: { title: 'Menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: { title: 'Menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: { title: 'Menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: { title: 'Menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: 'menu2' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  {
    path: '/子程序审核',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '子程序审核',
        component: () => import('@/views/子程序审核/index'),
        meta: { title: '子程序审核', icon: 'form' }
      }
    ]
  },


  // {
  //   path: '/案由管理',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: '案由管理',
  //       component: () => import('@/views/案由管理/index'),
  //       meta: { title: '案由管理', icon: 'form' }
  //     }
  //   ]
  // },


  // {
  //   path: '/信息项管理',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: '信息项管理',
  //       component: () => import('@/views/信息项管理/index'),
  //       meta: { title: '信息项管理', icon: 'form' }
  //     }
  //   ]
  // },


  // {
  //   path: '/子程序上传',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: '子程序上传',
  //       component: () => import('@/views/子程序上传/index'),
  //       meta: { title: '子程序上传', icon: 'form' }
  //     }
  //   ]
  // },


  // {
  //   path: '/信息项抽取',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: '信息项抽取',
  //       component: () => import('@/views/信息项抽取/index'),
  //       meta: { title: '信息项抽取', icon: 'form' }
  //     }
  //   ]
  // },


  // 注释掉，解决动态路由页面刷新404问题
  // // 404 page must be placed at the end !!!
  // { path: '*', redirect: '/404', hidden: true }
];



const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
