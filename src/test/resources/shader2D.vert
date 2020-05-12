#version 150 core

in vec2 position;
in vec4 color;
in vec2 texcoord;

out vec4 vertexColor;
out vec2 textureCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform mat4 projView;

void main() {
    vertexColor = color;
    textureCoord = texcoord;
    
    mat4 mvp = projView * model;
    vec3 pos = vec3(position, 0.0);
    
    gl_Position = mvp * vec4(pos, 1.0);
}
