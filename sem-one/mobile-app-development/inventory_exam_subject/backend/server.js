const Koa = require('koa');
const app = new Koa();
const server = require('http').createServer(app.callback());
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');

app.use(bodyparser());
app.use(cors());

app.use(async function (ctx, next) {
    const start = new Date();
    await next();
    const ms = new Date() - start;
    console.log(`${ctx.method} ${ctx.url} - ${ms}ms`);
});

app.use(async (ctx, next) => {
    await new Promise(resolve => setTimeout(resolve, 1000));
    await next();
});

const c = String.fromCharCode(97 + Date.now() % 20);

const products = Array.from(Array(30).keys())
    .map(id => ({ id, name: `${c}${id}` }));

const items = Array.from(Array(30).keys())
    .map(id => ({ productId: id, quantity: Math.round(Math.random() * 5), version: 1 }));

const router = new Router();

router.get('/product', ctx => {
    ctx.response.body = products;
    ctx.response.status = 200;
});

router.get('/item', ctx => {
    ctx.response.body = items;
    ctx.response.status = 200;
});

router.put('/item/:productId', ctx => {
    const item = ctx.request.body;
    const productId = parseInt(ctx.params.productId);
    const index = items.findIndex(it => it.productId === productId);
    if (productId !== item.productId || index === -1) {
        ctx.response.body = { text: 'Item not found' };
        ctx.response.status = 400;
    } else if (item.version < items[index].version) {
        ctx.response.body = { text: 'Version conflict' };
        ctx.response.status = 409;
    } else {
        item.version++;
        items[index] = item;
        ctx.response.body = item;
        ctx.response.status = 200;
    }
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
