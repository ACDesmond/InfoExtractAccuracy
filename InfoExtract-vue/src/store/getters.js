// 实时监听state值的最新状态，这里的getters可以理解为计算属性
// 在组件中是通过this.$store.getters.name来获取
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  visitedviews: state => state.tagsview.visitedviews,
  cachedViews: state => state.tagsView.cachedViews,
  roles: state => state.user.roles,
  permission_routes: state => state.permission.routes
}
export default getters
