"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.getTransforms = exports.indexHtmlTransformFactory = exports.customWebpackConfigTransformFactory = void 0;
const path = require("node:path");
const core_1 = require("@angular-devkit/core");
const common_1 = require("@angular-builders/common");
const custom_webpack_builder_1 = require("./custom-webpack-builder");
const customWebpackConfigTransformFactory = (options, { workspaceRoot, target, logger }) => browserWebpackConfig => {
    return custom_webpack_builder_1.CustomWebpackBuilder.buildWebpackConfig((0, core_1.normalize)(workspaceRoot), options.customWebpackConfig, browserWebpackConfig, options, target, logger);
};
exports.customWebpackConfigTransformFactory = customWebpackConfigTransformFactory;
const indexHtmlTransformFactory = (options, { workspaceRoot, target, logger }) => {
    if (!options.indexTransform)
        return null;
    const transformPath = path.join((0, core_1.getSystemPath)((0, core_1.normalize)(workspaceRoot)), options.indexTransform);
    const tsConfig = path.join((0, core_1.getSystemPath)((0, core_1.normalize)(workspaceRoot)), options.tsConfig);
    return (indexHtml) => __awaiter(void 0, void 0, void 0, function* () {
        const transform = yield (0, common_1.loadModule)(transformPath, tsConfig, logger);
        return transform(target, indexHtml);
    });
};
exports.indexHtmlTransformFactory = indexHtmlTransformFactory;
const getTransforms = (options, context) => ({
    webpackConfiguration: (0, exports.customWebpackConfigTransformFactory)(options, context),
    indexHtml: (0, exports.indexHtmlTransformFactory)(options, context),
});
exports.getTransforms = getTransforms;
//# sourceMappingURL=transform-factories.js.map