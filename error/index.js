const Jimp = require('jimp');
const BitSet = require('fast-bitset');

const { fitLin } = require('labs-fitter');
const Decimal = require('decimal.js');

const power2 = parseInt(process.argv[2]) + 1;
const power3 = parseInt(process.argv[3]) + 1;

const p2 = power2;
const p3 = power3;

const main = () => {
            console.log(`p2 : ${p2}\np3 : ${p3}`);
            const dim = getDim(p2, p3);
            console.log(`dim : ${dim}`);
};

const fit = values => {
    const ZERO = new Decimal('10e-100');
    let toVal = e => ({ value : new Decimal(e), error: ZERO  });

    let xv = values.map(e => e[0]).map(toVal);
    let yv = values.map(e => e[1]).map(toVal);

    return fitLin(xv, yv);
}

const pow = Math.pow;
const ln = Math.log;

const log = v => {
    console.log(v);
    return v;
};

const empty = len => new Array(len).fill(0);
const range_r = len => empty(len).map((_, i) => i);
const range = (iniOrLen, len) => len ? range_r(len - iniOrLen).map(i => i + iniOrLen) : range_r(iniOrLen);

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
    for (let i = 0; i < bit; i++) {
        line.set(offset + bit + i);
    }
    cantor_i(line, offset + 0, bit);
    cantor_i(line, offset + 2*bit, bit);
};

const cantor = line => cantor_i(line, 0, line.size);

const bxc_i = (line, offset, size) => {
    if (size % 2) return;
    const bit = size / 2;
    line[offset + bit] = 2;
    bxc_i(line, offset, bit);
    bxc_i(line, offset + bit, bit);
};
const bxc = line => bxc_i(line, 0, line.size);

const findAnyOnRange = (line, start, end) => {
  const nxt = line.nextUnsetBit(start);
  return (nxt !== -1) && (nxt <= end);
};

const bxcd = line => {
	let l = line.size, tt = 0;
	while (l % 2 === 0) {
		l /= 2;
		tt++;
	}
	const pairs = range(tt).map(t => {
		const t2 = pow(2, t);
		const squares = range(t2).filter(i => findAnyOnRange(line, i*l, (i+1)*l)).length;
		return [ln(t2), ln(squares)]; // x, y
	});
	return fit(pairs)[0].value.toSD(5).toString();
};

const getDim = (power2, power3) => {
    const size = pow(2, power2) * pow(3, power3);

    const line = new BitSet(size);
    line.size = size; // really, BitSet?
    cantor(line);

    return bxcd(line);
};

// log(dim);

// bxc(line);
// save(line.map(i => empty(size / 9).map(() => i)), 'test.png');

// should be 0.6309	(i.e., log_3(2))

main();
