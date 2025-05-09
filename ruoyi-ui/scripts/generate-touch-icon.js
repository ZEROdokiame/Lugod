const fs = require('fs');
const { createCanvas } = require('canvas');

// 创建180x180的图像（iOS建议尺寸）
const canvas = createCanvas(180, 180);
const ctx = canvas.getContext('2d');

// 绘制白色背景
ctx.fillStyle = 'white';
ctx.fillRect(0, 0, 180, 180);

// 绘制圆圈
ctx.beginPath();
ctx.arc(90, 90, 80, 0, Math.PI * 2);
ctx.strokeStyle = '#30B08F';
ctx.lineWidth = 10;
ctx.stroke();

// 绘制字母L
ctx.font = 'bold 80px Arial';
ctx.fillStyle = '#304156';
ctx.textAlign = 'center';
ctx.textBaseline = 'middle';
ctx.fillText('L', 90, 100);

// 绘制上下弧线
ctx.beginPath();
ctx.moveTo(40, 50);
ctx.quadraticCurveTo(90, 30, 140, 50);
ctx.strokeStyle = '#4AB7BD';
ctx.lineWidth = 8;
ctx.stroke();

ctx.beginPath();
ctx.moveTo(40, 130);
ctx.quadraticCurveTo(90, 150, 140, 130);
ctx.strokeStyle = '#4AB7BD';
ctx.lineWidth = 8;
ctx.stroke();

// 保存为PNG
const buffer = canvas.toBuffer('image/png');
fs.writeFileSync('public/apple-touch-icon.png', buffer);

console.log('Apple touch icon 生成成功！');
