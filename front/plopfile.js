module.exports = function (plop) {
    plop.setGenerator('component', {
        description: 'Crea un nuevo componente React',
        prompts: [
            {
                type: 'input',
                name: 'name',
                message: 'Nombre del componente:',
            },
        ],
        actions: [
            {
                type: 'add',
                path: 'src/components/{{pascalCase name}}/{{pascalCase name}}.jsx',
                templateFile: 'plop-templates/Component.jsx.hbs',
            },
            {
                type: 'add',
                path: 'src/components/{{pascalCase name}}/{{pascalCase name}}.css',
                templateFile: 'plop-templates/Component.css.hbs',
            },
        ],
    });
};
