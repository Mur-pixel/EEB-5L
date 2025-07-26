const { zip } = require("zip-a-folder");
const fs = require("fs");
const path = require("path");

(async () => {
    try {
        const source = path.resolve("dist/@mf-types/index.d.ts");
        const target = path.resolve("dist/@mf-types.d.ts");

        if (fs.existsSync(source)) {
            fs.copyFileSync(source, target);
            console.log("✅ index.d.ts → @mf-types.d.ts 복사 완료");
        } else {
            console.error("❌ index.d.ts 없음");
            process.exit(1);
        }

        await zip("dist/@mf-types", "dist/@mf-types.zip");
        console.log("✅ dist/@mf-types.zip 생성 완료!");
    } catch (err) {
        console.error("❌ 압축 중 오류 발생:", err);
        process.exit(1);
    }
})();