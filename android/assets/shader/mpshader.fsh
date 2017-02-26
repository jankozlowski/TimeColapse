varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform sampler2D u_sampler2D;
uniform sampler2D u_texture1;
uniform float u_time;
uniform float u_mp;
uniform float u_scale;

void main() {
	vec4 color = texture2D(u_sampler2D, v_texCoord0 *u_scale) * v_color;
	vec4 color2 = texture2D(u_texture1, v_texCoord0*u_scale) * v_color;
       
       if(color.a > 0.95 && u_mp >=1.0 && color.r <u_mp){
    	vec2 u = gl_FragCoord.xy / u_resolution,
        c = vec2(.5) - u;
     
        float t = u_time,
        z = atan(c.y,c.x) * 3.0,
        v = cos(z + sin(t * .2)) + .5 + sin(u.x*10.+t*1.3) * .4;
     
        gl_FragColor = vec4(mix(
                                vec3(v, sin(v * 4.) * .6, sin(v * 2.) * .9),
                                vec3(.8 + cos(z - t*.2) + .6 + sin(u.y*10.+t*1.5) * .9),
                                vec3(0.4,.6,.9)
        ),1);
    
    }
      else if (color.a > 0.95 && color.r <u_mp ) {
           gl_FragColor = color2;
    }
  
     else {
    
        // Otherwise, keep the original color
        
        gl_FragColor = vec4(0.0,1.0,0.0,0.0);
        
    }
}