const Jimp = require("jimp");

const log = v => {
	console.log(v);
	return v;
};

const empty = len => new Array(len).fill(0);
const range = len => empty(len).map((_, i) => i);

const gray = c => (data, idx) => range(3).forEach(i => data[idx + i] = c);
const colors = [ gray(0), gray(255), (data, idx) => data[idx] = 255 ];
const save = (bitmap, output) => { 
    const image = new Jimp(bitmap.length, bitmap[0].length, function (err, image) {
        if (err) throw err;
        image.scan(0, 0, image.bitmap.width, image.bitmap.height, (x, y, idx) => {
            const data = this.bitmap.data;
            colors[bitmap[x][y]](data, idx);
            data[idx + 3] = 255;
        });
        image.write(output);
    });
};

const cantor_i = (line, offset, size) => {
    if (size % 3) return;
    const bit = size / 3;
    range(bit).map(i => line[offset + bit + i] = 1);
    cantor_i(line, offset + 0, bit);
    cantor_i(line, offset + 2*bit, bit);
};
const cantor = line => cantor_i(line, 0, line.length);

const bxc_i = (line, offset, size) => {
    if (size % 2) return;
    const bit = size / 2;
    line[offset + bit] = 2;
    bxc_i(line, offset, bit);
    bxc_i(line, offset + bit, bit);
};
const bxc = line => bxc_i(line, 0, line.length);

const bxcd = line => {
	let l = line.length, t = 0;
	while (l % 2 === 0) {
		l /= 2;
		t++;
	}
	const t2 = Math.pow(2, t);
	return range(t2).filter(i => line.slice(i*l, (i+1)*l).some(e => !e)).length;
}

const size = Math.pow(2, 5) * Math.pow(3, 2);

const line = empty(size);
cantor(line);
const dim = bxcd(line);
console.log(dim);
bxc(line);
save(line.map(i => empty(size / 9).map(() => i)), 'test.png');

