const req = require.context('../../assets/icons/svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys()

const re = /\.\/(.*)\.svg/

const svgIcons = requireAll(req).map(i => {
  return i.match(re)[1]
})

// Font Awesome 4.7.0 图标列表
const fontAwesomeIcons = [
  // 常用图标
  'fa fa-home', 'fa fa-user', 'fa fa-users', 'fa fa-cog', 'fa fa-cogs',
  'fa fa-search', 'fa fa-edit', 'fa fa-trash', 'fa fa-plus', 'fa fa-minus',
  'fa fa-times', 'fa fa-check', 'fa fa-star', 'fa fa-heart', 'fa fa-bell',

  // 医院/医疗相关图标
  'fa fa-hospital-o', 'fa fa-user-md', 'fa fa-stethoscope', 'fa fa-heartbeat',
  'fa fa-medkit', 'fa fa-pills', 'fa fa-syringe', 'fa fa-ambulance',
  'fa fa-plus-square', 'fa fa-h-square', 'fa fa-wheelchair',

  // 商业/办公图标
  'fa fa-building', 'fa fa-building-o', 'fa fa-briefcase', 'fa fa-calculator',
  'fa fa-calendar', 'fa fa-calendar-o', 'fa fa-clipboard', 'fa fa-desktop',
  'fa fa-laptop', 'fa fa-tablet', 'fa fa-mobile', 'fa fa-phone',

  // 文件/文档图标
  'fa fa-file', 'fa fa-file-o', 'fa fa-file-text', 'fa fa-file-text-o',
  'fa fa-folder', 'fa fa-folder-o', 'fa fa-folder-open', 'fa fa-folder-open-o',
  'fa fa-archive', 'fa fa-database', 'fa fa-cloud', 'fa fa-download',
  'fa fa-upload', 'fa fa-print', 'fa fa-save', 'fa fa-copy',

  // 导航/箭头图标
  'fa fa-arrow-up', 'fa fa-arrow-down', 'fa fa-arrow-left', 'fa fa-arrow-right',
  'fa fa-chevron-up', 'fa fa-chevron-down', 'fa fa-chevron-left', 'fa fa-chevron-right',
  'fa fa-angle-up', 'fa fa-angle-down', 'fa fa-angle-left', 'fa fa-angle-right',

  // 媒体/社交图标
  'fa fa-play', 'fa fa-pause', 'fa fa-stop', 'fa fa-volume-up', 'fa fa-volume-down',
  'fa fa-volume-off', 'fa fa-music', 'fa fa-video-camera', 'fa fa-camera',
  'fa fa-image', 'fa fa-picture-o', 'fa fa-film', 'fa fa-microphone',

  // 交通/地图图标
  'fa fa-car', 'fa fa-taxi', 'fa fa-bus', 'fa fa-truck', 'fa fa-plane',
  'fa fa-ship', 'fa fa-bicycle', 'fa fa-motorcycle', 'fa fa-map',
  'fa fa-map-marker', 'fa fa-location-arrow', 'fa fa-compass',

  // 购物/电商图标
  'fa fa-shopping-cart', 'fa fa-shopping-bag', 'fa fa-credit-card',
  'fa fa-money', 'fa fa-dollar', 'fa fa-euro', 'fa fa-yen', 'fa fa-bitcoin',
  'fa fa-paypal', 'fa fa-cc-visa', 'fa fa-cc-mastercard',

  // 天气/时间图标
  'fa fa-sun-o', 'fa fa-moon-o', 'fa fa-cloud', 'fa fa-umbrella',
  'fa fa-snowflake-o', 'fa fa-thermometer', 'fa fa-clock-o', 'fa fa-hourglass',

  // 工具/设备图标
  'fa fa-wrench', 'fa fa-screwdriver', 'fa fa-hammer', 'fa fa-key',
  'fa fa-lock', 'fa fa-unlock', 'fa fa-shield', 'fa fa-eye', 'fa fa-eye-slash',
  'fa fa-lightbulb-o', 'fa fa-flash', 'fa fa-fire', 'fa fa-battery-full',

  // 状态/警告图标
  'fa fa-info', 'fa fa-info-circle', 'fa fa-warning', 'fa fa-exclamation',
  'fa fa-exclamation-triangle', 'fa fa-question', 'fa fa-question-circle',
  'fa fa-check-circle', 'fa fa-times-circle', 'fa fa-thumbs-up', 'fa fa-thumbs-down',

  // 教育/学习图标
  'fa fa-book', 'fa fa-graduation-cap', 'fa fa-university', 'fa fa-pencil',
  'fa fa-pencil-square', 'fa fa-eraser', 'fa fa-globe', 'fa fa-trophy',
  'fa fa-certificate', 'fa fa-bookmark', 'fa fa-tags', 'fa fa-tag',

  // 体育/娱乐图标
  'fa fa-gamepad', 'fa fa-puzzle-piece', 'fa fa-futbol-o', 'fa fa-basketball-ball',
  'fa fa-football-ball', 'fa fa-baseball-ball', 'fa fa-tennis-ball',
  'fa fa-table-tennis', 'fa fa-bowling-ball', 'fa fa-golf-ball',

  // 食物/餐饮图标
  'fa fa-cutlery', 'fa fa-coffee', 'fa fa-birthday-cake', 'fa fa-glass',
  'fa fa-beer', 'fa fa-wine', 'fa fa-apple', 'fa fa-lemon-o',

  // 通信/联系图标
  'fa fa-envelope', 'fa fa-envelope-o', 'fa fa-comment', 'fa fa-comment-o',
  'fa fa-comments', 'fa fa-comments-o', 'fa fa-phone', 'fa fa-fax',
  'fa fa-wifi', 'fa fa-signal', 'fa fa-bluetooth', 'fa fa-rss',

  // 品牌图标
  'fa fa-facebook', 'fa fa-twitter', 'fa fa-instagram', 'fa fa-linkedin',
  'fa fa-youtube', 'fa fa-google', 'fa fa-apple', 'fa fa-microsoft',
  'fa fa-amazon', 'fa fa-github', 'fa fa-skype', 'fa fa-whatsapp'
]

// 合并SVG图标和Font Awesome图标
const icons = [...svgIcons, ...fontAwesomeIcons]

export default icons
