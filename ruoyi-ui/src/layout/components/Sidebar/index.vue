<template>
    <div :class="{'has-logo':showLogo, 'sidebar-container': true}" :style="{ backgroundColor: settings.sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
        <logo v-if="showLogo" :collapse="isCollapse" />
        <el-scrollbar :class="settings.sideTheme" wrap-class="scrollbar-wrapper">
            <el-menu
                :default-active="activeMenu"
                :collapse="isCollapse"
                :background-color="settings.sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground"
                :text-color="settings.sideTheme === 'theme-dark' ? variables.menuColor : variables.menuLightColor"
                :unique-opened="true"
                :active-text-color="settings.theme"
                :collapse-transition="false"
                mode="vertical"
                class="custom-sidebar-menu"
            >
                <sidebar-item
                    v-for="(route, index) in sidebarRouters"
                    :key="route.path + index"
                    :item="route"
                    :base-path="route.path"
                />
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
import { mapGetters, mapState } from "vuex"
import Logo from "./Logo"
import SidebarItem from "./SidebarItem"
import variables from "@/assets/styles/variables.scss"

export default {
    components: { SidebarItem, Logo },
    computed: {
        ...mapState(["settings"]),
        ...mapGetters(["sidebarRouters", "sidebar"]),
        activeMenu() {
            const route = this.$route
            const { meta, path } = route
            // if set path, the sidebar will highlight the path you set
            if (meta.activeMenu) {
                return meta.activeMenu
            }
            return path
        },
        showLogo() {
            return this.$store.state.settings.sidebarLogo
        },
        variables() {
            return variables
        },
        isCollapse() {
            return !this.sidebar.opened
        }
    }
}
</script>

<style lang="scss" scoped>
.sidebar-container {
  transition: width 0.28s;
  box-shadow: 1px 0 6px rgba(0, 0, 0, 0.2);
}

// 自定义侧边栏菜单样式
.custom-sidebar-menu {
  border-right: none !important;
}

// 菜单项
.sidebar-container >>> .el-menu-item {
  height: 50px;
  line-height: 50px;
  padding-left: 20px !important;
  border-left: 3px solid transparent;
  transition: all 0.3s;
  
  &:hover {
    background-color: rgba(255, 255, 255, 0.1) !important;
  }
  
  &.is-active {
    border-left: 3px solid var(--theme);
    background-color: rgba(255, 255, 255, 0.15) !important;
    
    // 包含图标
    i {
      color: var(--theme) !important;
    }
  }
  
  // 图标样式
  i {
    margin-right: 10px;
    font-size: 18px;
    transition: all 0.3s;
  }
}

// 子菜单
.sidebar-container >>> .el-submenu {
  .el-submenu__title {
    height: 50px;
    line-height: 50px;
    padding-left: 20px !important;
    transition: all 0.3s;
    border-left: 3px solid transparent;
    
    &:hover {
      background-color: rgba(255, 255, 255, 0.1) !important;
    }
    
    // 图标样式
    i {
      margin-right: 10px;
      font-size: 18px;
      transition: all 0.3s;
    }
  }
}

// 子菜单打开状态
.sidebar-container >>> .el-submenu.is-opened {
  > .el-submenu__title {
    i.el-submenu__icon-arrow {
      transform: rotateZ(-90deg);
    }
  }
}

// 子菜单项
.sidebar-container >>> .el-submenu .el-menu-item {
  min-width: auto;
  padding-left: 50px !important;
  background-color: transparent !important;
  border-left: 3px solid transparent;
  
  &.is-active {
    border-left: 3px solid var(--theme);
    background-color: rgba(255, 255, 255, 0.15) !important;
  }
}

// 滚动条样式
.scrollbar-wrapper {
  overflow-x: hidden !important;
}
.sidebar-container >>> .scrollbar-wrapper::-webkit-scrollbar {
  width: 6px;
}
.sidebar-container >>> .scrollbar-wrapper::-webkit-scrollbar-track {
  background-color: transparent;
}
.sidebar-container >>> .scrollbar-wrapper::-webkit-scrollbar-thumb {
  border-radius: 3px;
  background-color: rgba(255, 255, 255, 0.3);
}
.sidebar-container >>> .scrollbar-wrapper:hover::-webkit-scrollbar-thumb {
  background-color: rgba(255, 255, 255, 0.5);
}

// 折叠动画
.sidebar-container {
  .sidebar-hidden-icon {
    display: none;
  }
}
</style>
