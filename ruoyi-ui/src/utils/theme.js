// 主题工具函数

/**
 * 设置主题色
 * @param {string} color - 主题色（如：#409EFF）
 */
export function setThemeColor(color) {
  document.documentElement.style.setProperty('--theme', color);
}

/**
 * 初始化主题设置
 * @param {object} settings - 设置对象，包含theme属性
 */
export function initTheme(settings) {
  if (settings && settings.theme) {
    setThemeColor(settings.theme);
  }
}
